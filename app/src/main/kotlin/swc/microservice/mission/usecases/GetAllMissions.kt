package swc.microservice.mission.usecases

import swc.microservice.mission.entities.Mission
import swc.microservice.mission.entities.TypeOfMission
import swc.microservice.mission.entities.Waste
import swc.microservice.mission.usecases.managers.ManagerSupplier

class GetAllMissions : MissionUseCase<List<Mission<Waste>>> {
    override fun execute(manager: ManagerSupplier): List<Mission<Waste>> =
        manager.mission(TypeOfMission.ORDINARY).getMissions() + manager.mission(TypeOfMission.EXTRAORDINARY).getMissions()
}
