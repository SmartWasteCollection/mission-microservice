package swc.microservice.mission.usecases

import swc.microservice.mission.entities.Mission
import swc.microservice.mission.entities.Truck
import swc.microservice.mission.entities.Waste
import swc.microservice.mission.usecases.managers.ManagerSupplier

class AssignTruckToMission(private val missionId: String, private val truck: Truck) : MissionUseCase<Mission<Waste>?> {
    override fun execute(manager: ManagerSupplier): Mission<Waste>? =
        manager.mission().getMissionById(missionId)
            ?.let { manager.mission().assignTruckToMission(it.missionId, truck.id) }
}
