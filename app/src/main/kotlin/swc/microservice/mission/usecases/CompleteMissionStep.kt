package swc.microservice.mission.usecases

import swc.microservice.mission.entities.Mission
import swc.microservice.mission.entities.Waste

class CompleteMissionStep<T : Waste>(private val missionId: String) : MissionUseCase<Mission<T>> {
    override fun execute(manager: MissionManager): Mission<T> = manager.completeMissionStep(this.missionId)
}
