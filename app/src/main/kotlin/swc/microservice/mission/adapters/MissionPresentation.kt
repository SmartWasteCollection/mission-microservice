package swc.microservice.mission.adapters

import com.azure.digitaltwins.core.BasicDigitalTwin
import com.azure.digitaltwins.core.BasicDigitalTwinMetadata
import com.azure.digitaltwins.core.BasicRelationship
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import swc.microservice.mission.adapters.MissionPresentation.Values.COMPLETED
import swc.microservice.mission.adapters.MissionPresentation.Values.DATE
import swc.microservice.mission.adapters.MissionPresentation.Values.STEP_RELATIONSHIP_NAME
import swc.microservice.mission.adapters.MissionPresentation.Values.TRUCK_RELATIONSHIP_NAME
import swc.microservice.mission.adapters.MissionPresentation.Values.TYPE_OF_MISSION
import swc.microservice.mission.adapters.MissionPresentation.Values.TYPE_OF_WASTE
import swc.microservice.mission.drivers.digitaltwins.DigitalTwinsValues.MISSION_MODEL_ID
import swc.microservice.mission.entities.ExtraordinaryWaste
import swc.microservice.mission.entities.Mission
import swc.microservice.mission.entities.MissionStep
import swc.microservice.mission.entities.Waste
import swc.microservice.mission.entities.toOrdinaryTypeOfWaste
import swc.microservice.mission.entities.toTypeOfMission
import swc.microservice.mission.entities.toTypeOfWaste
import java.time.LocalDate

object MissionPresentation {

    private object Values {
        const val STEP_RELATIONSHIP_NAME = "missionHasStep"
        const val TRUCK_RELATIONSHIP_NAME = "missionHasTruck"
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
            missionSteps[index].stepId,
            STEP_RELATIONSHIP_NAME
        ).addProperty(COMPLETED, missionSteps[index].completed)

        /**
         * Serializes a relationship of a [Mission] to a truck.
         */
        fun Mission<*>.truckRelationship(truckId: String): BasicRelationship = BasicRelationship(
            "${this.missionId}-$truckId",
            missionId,
            truckId,
            TRUCK_RELATIONSHIP_NAME
        )
    }

    object Deserialization {

        /**
         * Deserializes a [BasicDigitalTwin] into a [Mission].
         */
        fun BasicDigitalTwin.toMission(relationships: List<BasicRelationship>): Mission<*> {
            return Mission(
                missionId = this.id,
                truckId = relationships.find { it.name == TRUCK_RELATIONSHIP_NAME }?.targetId,
                date = LocalDate.parse(this.contents[DATE].toString()),
                typeOfWaste = this.contents[TYPE_OF_WASTE].toString().toOrdinaryTypeOfWaste(),
                typeOfMission = this.contents[TYPE_OF_MISSION].toString().toTypeOfMission(),
                missionSteps = relationships.filter { it.name == STEP_RELATIONSHIP_NAME }
                    .map { MissionStep(it.targetId, it.properties[COMPLETED] as Boolean) }
            )
        }

        class WasteDeserializer(vc: Class<*> = Waste::class.java) : StdDeserializer<Waste>(vc) {
            override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): Waste =
                p?.valueAsString?.toTypeOfWaste()?.wasteName ?: ExtraordinaryWaste.OTHER
        }
    }
}
