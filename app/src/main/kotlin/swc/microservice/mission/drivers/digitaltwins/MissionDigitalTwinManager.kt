package swc.microservice.mission.drivers.digitaltwins

import com.azure.digitaltwins.core.BasicDigitalTwin
import com.azure.digitaltwins.core.BasicRelationship
import com.azure.digitaltwins.core.DigitalTwinsClient
import com.azure.digitaltwins.core.DigitalTwinsClientBuilder
import com.azure.identity.AzureCliCredentialBuilder
import swc.microservice.mission.adapters.MissionPresentation.Deserialization.toMission
import swc.microservice.mission.adapters.MissionPresentation.Serialization.stepRelationship
import swc.microservice.mission.adapters.MissionPresentation.Serialization.toDigitalTwin
import swc.microservice.mission.adapters.MissionPresentation.Serialization.truckRelationship
import swc.microservice.mission.drivers.digitaltwins.DigitalTwinsValues.ENDPOINT
import swc.microservice.mission.entities.Mission
import swc.microservice.mission.entities.Waste
import swc.microservice.mission.usecases.managers.MissionManager
import java.util.UUID

class MissionDigitalTwinManager : MissionManager {

    private val client: DigitalTwinsClient = DigitalTwinsClientBuilder()
        .credential(AzureCliCredentialBuilder().build())
        .endpoint(ENDPOINT)
        .buildClient()

    /**
     * Gets the digital twin of a [Mission].
     */
    override fun getMissionById(missionId: String): Mission<Waste>? = try {
        client.getDigitalTwin(missionId, BasicDigitalTwin::class.java).toMission(
            client.listRelationships(missionId, BasicRelationship::class.java).toList()
        )
    } catch (e: Exception) {
        println(e)
        null
    }

    override fun getMissions(): List<Mission<Waste>> =
        client.query(
            "SELECT * FROM digitaltwins WHERE \$metadata.\$model = 'dtmi:swc:Mission;1'",
            BasicDigitalTwin::class.java
        ).map { it.toMission(client.listRelationships(it.id, BasicRelationship::class.java).toList()) }

    /**
     * Creates the digital twin of a [Mission] (including its relationships with trucks and collection points).
     */
    override fun createMission(mission: Mission<Waste>): String {
        val twin = createDigitalTwin(mission.toDigitalTwin())
        (0 until mission.missionSteps.size).forEach { createStepRelationship(mission, it) }
        return twin.id
    }

    override fun createNewMissionId(): String = "Mission-${UUID.randomUUID()}"

    /**
     * Deploys a [BasicDigitalTwin].
     */
    fun createDigitalTwin(twin: BasicDigitalTwin): BasicDigitalTwin =
        client.createOrReplaceDigitalTwin(twin.id, twin, BasicDigitalTwin::class.java)

    /**
     * Deploys a _MissionHasStep_ relationship.
     */
    private fun createStepRelationship(mission: Mission<Waste>, index: Int): String {
        val relationship = mission.stepRelationship(index)
        return client.createOrReplaceRelationship(
            mission.missionId,
            relationship.id,
            relationship,
            BasicRelationship::class.java
        ).id
    }

    private fun createTruckRelationship(mission: Mission<Waste>, truckId: String): String {
        val relationship = mission.truckRelationship(truckId)
        return client.createOrReplaceRelationship(
            mission.missionId,
            relationship.id,
            relationship,
            BasicRelationship::class.java
        ).id
    }

    /**
     * Completes a _MissionHasStep_ relationship.
     */
    override fun completeMissionStep(missionId: String): Mission<Waste>? = getMissionById(missionId)?.let {
        val index = it.missionSteps.indexOfFirst { m -> !m.completed }
        it.missionSteps[index].completed = true
        createStepRelationship(it, index)
        return it
    }

    /**
     * Creates a _MissionHasTruck_ relationship.
     */
    override fun assignTruckToMission(missionId: String, truckId: String): Mission<Waste>? {
        val mission = this.getMissionById(missionId)
        mission?.truckId = truckId
        if (mission != null) {
            createTruckRelationship(mission, truckId)
        }
        return mission
    }

    /**
     * Deletes a [Mission]'s digital twin.
     */
    fun deleteMission(missionId: String): Mission<*>? {
        val mission = getMissionById(missionId)
        client.listRelationships(missionId, BasicRelationship::class.java)
            .toList()
            .forEach { client.deleteRelationship(missionId, it.id) }
        deleteDigitalTwin(missionId)
        return mission
    }

    /**
     * Deletes the digital twin with the specified id.
     */
    fun deleteDigitalTwin(id: String) {
        client.deleteDigitalTwin(id)
    }
}
