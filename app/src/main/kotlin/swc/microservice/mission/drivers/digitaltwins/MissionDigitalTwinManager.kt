package swc.microservice.mission.drivers.digitaltwins

import com.azure.digitaltwins.core.DigitalTwinsClient
import com.azure.digitaltwins.core.DigitalTwinsClientBuilder
import com.azure.identity.AzureCliCredentialBuilder
import swc.microservice.mission.adapters.MissionPresentation.Serialization.getRelationshipJsonString
import swc.microservice.mission.adapters.MissionPresentation.Serialization.toJsonString
import swc.microservice.mission.drivers.digitaltwins.DigitalTwinsValues.ENDPOINT
import swc.microservice.mission.entities.Mission

class MissionDigitalTwinManager {

    private val client: DigitalTwinsClient = DigitalTwinsClientBuilder()
        .credential(AzureCliCredentialBuilder().build())
        .endpoint(ENDPOINT)
        .buildClient()

    fun getMission(missionId: String): String? = try {
        client.getDigitalTwin(missionId, String::class.java)
    } catch (e: Exception) {
        println(e)
        null
    }

    fun createMission(mission: Mission<*>): String {
        client.createOrReplaceDigitalTwin(mission.missionId, mission.toJsonString(), String::class.java)
        return mission.missionId
    }

    private fun createStepRelationship(mission: Mission<*>): String {
        val relationshipId = "${mission.missionId}-${mission.truckId}"
        client.createOrReplaceRelationship(
            mission.missionId,
            relationshipId,
            mission.getRelationshipJsonString(DigitalTwinRelationship.TO_STEP),
            String::class.java
        )
        return relationshipId
    }

    private fun createTruckRelationship(mission: Mission<*>): String {
        val relationshipId = "${mission.missionId}-${mission.truckId}"
        client.createOrReplaceRelationship(
            mission.missionId,
            relationshipId,
            mission.getRelationshipJsonString(DigitalTwinRelationship.TO_TRUCK),
            String::class.java
        )
        return relationshipId
    }

    fun deleteMission(missionId: String): String? {
        val mission = getMission(missionId)
        client.deleteDigitalTwin(missionId)
        return mission
    }
}
