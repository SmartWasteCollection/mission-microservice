package swc.microservice.mission.usecases

import swc.microservice.mission.entities.Mission
import swc.microservice.mission.entities.TypeOfMission
import swc.microservice.mission.entities.Waste
import swc.microservice.mission.usecases.managers.ManagerSupplier

class CompleteMissionStep(private val missionId: String) : MissionUseCase<Mission<Waste>?> {
    override fun execute(manager: ManagerSupplier): Mission<Waste>? =
        (manager.mission(TypeOfMission.ORDINARY).getMissionById(missionId) ?: manager.mission(TypeOfMission.EXTRAORDINARY).getMissionById(missionId))?.let {
            manager.mission(it.typeOfMission).completeMissionStep(this.missionId)?.also { mission ->
                if (mission.typeOfMission == TypeOfMission.ORDINARY && mission.isCompleted()) {
                    manager.mission(TypeOfMission.ORDINARY).deleteMission(mission.missionId)
                    manager.mission(mission).createMission(mission)
                }
            }
        }
}
