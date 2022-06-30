package swc.microservice.mission.entities

import java.util.*

data class Mission<T : Waste>(
    var truckId: String? = null,
    val date: Date,
    val typeOfWaste: TypeOfWaste<T>,
    val typeOfMission: TypeOfMission,
    val missionSteps: List<MissionStep>
) {
    val missionId: String = truckId +
            date.toString() +
            typeOfWaste.toString() +
            typeOfMission.toString() +
            missionSteps.toString()
    fun isCompleted(): Boolean = missionSteps.all { it.completed }
}