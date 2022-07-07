package swc.microservice.mission.entities

import java.time.LocalDate

data class Mission<out T : Waste>(
    val missionId: String,
    var truckId: String? = null,
    val date: LocalDate = LocalDate.now(),
    val typeOfWaste: TypeOfWaste<out T>,
    val typeOfMission: TypeOfMission,
    val missionSteps: List<MissionStep>
) {
    fun isCompleted(): Boolean = missionSteps.all { it.completed }
}
