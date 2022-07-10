package swc.microservice.mission.usecases

import swc.microservice.mission.entities.Mission
import swc.microservice.mission.entities.TypeOfMission
import swc.microservice.mission.entities.Waste
import swc.microservice.mission.usecases.managers.ManagerSupplier

class AssignTruckToMission(private val missionId: String, private val truckId: String) : MissionUseCase<Mission<Waste>?> {
    override fun execute(manager: ManagerSupplier): Mission<Waste>? =
        (manager.mission(TypeOfMission.ORDINARY).getMissionById(missionId) ?: manager.mission(TypeOfMission.EXTRAORDINARY).getMissionById(missionId))?.let {
            manager.mission(it.typeOfMission).assignTruckToMission(it.missionId, truckId)
        }
}
