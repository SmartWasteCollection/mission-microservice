package swc.microservice.mission.adapters

import com.azure.digitaltwins.core.BasicDigitalTwin
import com.azure.digitaltwins.core.BasicDigitalTwinMetadata
import com.azure.digitaltwins.core.BasicRelationship
import swc.microservice.mission.adapters.MissionPresentation.Values.COMPLETED
import swc.microservice.mission.adapters.MissionPresentation.Values.DATE
import swc.microservice.mission.adapters.MissionPresentation.Values.STEP_RELATIONSHIP_NAME
import swc.microservice.mission.adapters.MissionPresentation.Values.TRUCK_RELATIONSHIP_NAME
import swc.microservice.mission.adapters.MissionPresentation.Values.TYPE_OF_MISSION
import swc.microservice.mission.adapters.MissionPresentation.Values.TYPE_OF_WASTE
import swc.microservice.mission.drivers.digitaltwins.DigitalTwinsValues.MISSION_MODEL_ID
import swc.microservice.mission.entities.Mission
import swc.microservice.mission.entities.MissionStep
import swc.microservice.mission.entities.TypeOfMission
import swc.microservice.mission.entities.toTypeOfWaste
import java.time.LocalDate

object MissionPresentation {

    private object Values {

        const val STEP_RELATIONSHIP_NAME = "MissionHasStep"
        const val TRUCK_RELATIONSHIP_NAME = "MissionHasTruck"
        const val DATE = "date"
        const val TYPE_OF_WASTE = "typeOfWaste"
        const val TYPE_OF_MISSION = "typeOfMission"
        const val COMPLETED = "completed"
    }

    object Serialization {

        /**
         * Serializes a [Mission] into a [BasicDigitalTwin].
         */
        fun Mission<*>.toDigitalTwin(): BasicDigitalTwin = BasicDigitalTwin(this.missionId)
            .setMetadata(BasicDigitalTwinMetadata().setModelId(MISSION_MODEL_ID))
            .addToContents(DATE, this.date)
            .addToContents(TYPE_OF_WASTE, this.typeOfWaste.wasteName.toString())
            .addToContents(TYPE_OF_MISSION, this.typeOfMission.toString())

        /**
         * Serializes a relationship of a [Mission] to a mission step.
         */
        fun Mission<*>.stepRelationship(index: Int): BasicRelationship = BasicRelationship(
            "${this.missionId}-$index",
            missionId,
            missionSteps[index].collectionPointId,
            STEP_RELATIONSHIP_NAME
        ).addProperty(COMPLETED, missionSteps[index].completed)
    }

    object Deserialization {

        fun BasicDigitalTwin.toMission(relationships: List<BasicRelationship>): Mission<*> {
            return Mission(
                missionId = this.id,
                truckId = relationships.first { it.name == TRUCK_RELATIONSHIP_NAME }.targetId,
                date = LocalDate.parse(this.contents[DATE].toString()),
                typeOfWaste = this.contents[TYPE_OF_WASTE].toString().toTypeOfWaste(),
                typeOfMission = if (this.contents[TYPE_OF_MISSION] == "ORDINARY") TypeOfMission.ORDINARY else TypeOfMission.EXTRAORDINARY,
                missionSteps = relationships.filter { it.name == STEP_RELATIONSHIP_NAME }.map { MissionStep(it.targetId, TODO(), it.properties[COMPLETED] as Boolean) }
            )
        }
    }
}
