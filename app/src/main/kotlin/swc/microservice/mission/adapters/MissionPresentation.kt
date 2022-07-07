package swc.microservice.mission.adapters

import swc.microservice.mission.adapters.MissionPresentation.Values.COMPLETED
import swc.microservice.mission.adapters.MissionPresentation.Values.DATE
import swc.microservice.mission.adapters.MissionPresentation.Values.METADATA
import swc.microservice.mission.adapters.MissionPresentation.Values.MISSION_MODEL
import swc.microservice.mission.adapters.MissionPresentation.Values.MISSION_STEP
import swc.microservice.mission.adapters.MissionPresentation.Values.MISSION_TRUCK
import swc.microservice.mission.adapters.MissionPresentation.Values.MODEL
import swc.microservice.mission.adapters.MissionPresentation.Values.RELATIONSHIP_NAME
import swc.microservice.mission.adapters.MissionPresentation.Values.SOURCE_ID
import swc.microservice.mission.adapters.MissionPresentation.Values.TARGET_ID
import swc.microservice.mission.adapters.MissionPresentation.Values.TYPE_OF_MISSION
import swc.microservice.mission.adapters.MissionPresentation.Values.TYPE_OF_WASTE
import swc.microservice.mission.drivers.digitaltwins.DigitalTwinRelationship
import swc.microservice.mission.drivers.digitaltwins.DigitalTwinRelationship.TO_STEP
import swc.microservice.mission.drivers.digitaltwins.DigitalTwinRelationship.TO_TRUCK
import swc.microservice.mission.entities.Mission

object MissionPresentation {

    private object Values {

        const val METADATA = "\$metadata"
        const val MODEL = "\$model"
        const val MISSION_MODEL = "dtmi:swc:Mission;1"
        const val DATE = "Date"
        const val TYPE_OF_WASTE = "TypeOfWaste"
        const val TYPE_OF_MISSION = "TypeOfMission"
        const val COMPLETED = "Completed"
        const val SOURCE_ID = "\$sourceId"
        const val TARGET_ID = "\$targetId"
        const val RELATIONSHIP_NAME = "\$relationshipName"
        const val MISSION_STEP = "MissionHasSteps"
        const val MISSION_TRUCK = "MissionHasTruck"
    }

    object Serialization {

        /**
         * Gets a json string from a [Mission].
         */
        fun Mission<*>.toJsonString(): String = json(
            Pair(METADATA, json(Pair(MODEL, "\"$MISSION_MODEL\""))),
            Pair(DATE, this.date),
            Pair(TYPE_OF_WASTE, this.typeOfWaste),
            Pair(TYPE_OF_MISSION, this.typeOfMission),
            Pair(COMPLETED, this.isCompleted())
        )

        /**
         * Gets a relationship json string of a [Mission], depending on the particular [DigitalTwinRelationship].
         */
        fun Mission<*>.getRelationshipJsonString(relationship: DigitalTwinRelationship): String = when (relationship) {
            TO_TRUCK -> json(
                Pair(SOURCE_ID, this.missionId),
                Pair(RELATIONSHIP_NAME, MISSION_TRUCK),
                Pair(TARGET_ID, this.truckId)
            )
            TO_STEP -> json(
                Pair(SOURCE_ID, this.missionId),
                Pair(RELATIONSHIP_NAME, MISSION_STEP),
                Pair(TARGET_ID, this.missionSteps.first { !it.completed })
            )
        }

        /**
         * Allows to convert a map of elements (represented by [Pair]s) into a Json String.
         */
        private fun <T> json(vararg pairs: Pair<String, T>): String = "{ ${
        pairs.map { "\"${it.first}\": ${it.second}" }
            .reduce { acc, s -> "$acc, $s" }
        } }"
    }
}
