package swc.microservice.mission.usecases

import swc.microservice.mission.entities.Mission
import swc.microservice.mission.entities.Waste
import swc.microservice.mission.usecases.managers.ManagerSupplier

class CompleteMissionStep(private val missionId: String) : MissionUseCase<Mission<Waste>?> {
    override fun execute(manager: ManagerSupplier): Mission<Waste>? = manager.mission().completeMissionStep(this.missionId)
}
