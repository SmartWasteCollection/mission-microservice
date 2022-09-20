package swc.microservice.mission.usecases

import swc.microservice.mission.entities.TypeOfMission
import swc.microservice.mission.usecases.managers.ManagerSupplier

class AssignTruckToMission(private val missionId: String) : MissionUseCase<Unit> {
    override fun execute(manager: ManagerSupplier) {
        manager.truck().getAvailableTrucks().firstOrNull()?.let { truck ->
            manager.truck().assignTruck(truck.truckId, true).also {
                (
                    manager.mission(TypeOfMission.ORDINARY).getMissionById(missionId)
                        ?: manager.mission(TypeOfMission.EXTRAORDINARY).getMissionById(missionId)
                    )?.let {
                    manager.mission(it.typeOfMission).assignTruckToMission(it.missionId, truck.truckId)
                }
            }
        }
    }
}
