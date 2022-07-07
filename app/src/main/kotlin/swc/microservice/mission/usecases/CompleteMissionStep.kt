package swc.microservice.mission.usecases

import swc.microservice.mission.entities.Mission
import swc.microservice.mission.entities.Waste

class CompleteMissionStep(private val missionId: String) : MissionUseCase<Mission<Waste>?> {
    override fun execute(manager: MissionManager): Mission<Waste>? = manager.completeMissionStep(this.missionId)
}
