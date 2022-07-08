package swc.microservice.mission.usecases

import swc.microservice.mission.entities.Mission
import swc.microservice.mission.entities.Waste
import swc.microservice.mission.usecases.managers.MissionManager

class CompleteMissionStep(private val missionId: String) : MissionUseCase<Mission<Waste>?> {
    override fun execute(manager: MissionManager): Mission<Waste>? = manager.completeMissionStep(this.missionId)
}
