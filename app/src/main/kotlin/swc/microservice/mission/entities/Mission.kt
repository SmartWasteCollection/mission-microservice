package swc.microservice.mission.entities

import java.util.*

data class Mission(
    val missionId: String,
    val truckId: String,
    val date: Date,
    val typeOfWaste: TypeOfWaste,
    val typeOfMission: TypeOfMission,
    val missionSteps: List<MissionStep>
) {
    fun isCompleted(): Boolean = missionSteps.all { it.completed }
}
