package swc.microservice.mission.drivers.digitaltwins

import com.azure.digitaltwins.core.BasicDigitalTwin
import com.azure.digitaltwins.core.BasicRelationship
import com.azure.digitaltwins.core.DigitalTwinsClient
import com.azure.digitaltwins.core.DigitalTwinsClientBuilder
import com.azure.identity.AzureCliCredentialBuilder
import swc.microservice.mission.adapters.MissionPresentation.Deserialization.toMission
import swc.microservice.mission.adapters.MissionPresentation.Serialization.stepRelationship
import swc.microservice.mission.adapters.MissionPresentation.Serialization.toDigitalTwin
import swc.microservice.mission.drivers.digitaltwins.DigitalTwinsValues.ENDPOINT
import swc.microservice.mission.entities.Mission

class MissionDigitalTwinManager {

    private val client: DigitalTwinsClient = DigitalTwinsClientBuilder()
        .credential(AzureCliCredentialBuilder().build())
        .endpoint(ENDPOINT)
        .buildClient()

    /**
     * Gets the digital twin of a [Mission].
     */
    fun getMission(missionId: String): Mission<*>? = try {
        client.getDigitalTwin(missionId, BasicDigitalTwin::class.java).toMission(
            client.listRelationships(missionId, BasicRelationship::class.java).toList()
        )
    } catch (e: Exception) {
        println(e)
        null
    }

    /**
     * Creates the digital twin of a [Mission] (including its relationships with trucks and collection points).
     */
    fun createMission(mission: Mission<*>): String {
        val missionDigitalTwin = mission.toDigitalTwin()
        val twin = createDigitalTwin(missionDigitalTwin)
        (0 until mission.missionSteps.size).forEach { createStepRelationship(mission, it) }
        return twin.id
    }

    /**
     * Deploys a [BasicDigitalTwin].
     */
    fun createDigitalTwin(twin: BasicDigitalTwin): BasicDigitalTwin =
        client.createOrReplaceDigitalTwin(twin.id, twin, BasicDigitalTwin::class.java)

    /**
     * Deploys a _MissionHasStep_ relationship.
     */
    private fun createStepRelationship(mission: Mission<*>, index: Int): String {
        val relationship = mission.stepRelationship(index)
        client.createOrReplaceRelationship(
            mission.missionId,
            relationship.id,
            relationship,
            BasicRelationship::class.java
        )
        return relationship.id
    }

    /**
     * Deletes a [Mission]'s digital twin.
     */
    fun deleteMission(missionId: String): Mission<*>? {
        val mission = getMission(missionId)
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
