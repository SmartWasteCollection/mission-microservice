package swc.microservice.mission.usecases

import swc.microservice.mission.entities.Mission
import swc.microservice.mission.entities.Truck
import swc.microservice.mission.entities.Waste
import swc.microservice.mission.usecases.managers.MissionManager

class AssignTruckToMission(private val missionId: String, private val truck: Truck) : MissionUseCase<Mission<Waste>?> {
    override fun execute(manager: MissionManager): Mission<Waste>? =
        manager.assignTruckToMission(this.missionId, this.truck)
}
