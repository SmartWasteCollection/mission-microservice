package swc.microservice.mission.usecases.events

import swc.microservice.mission.entities.Mission
import swc.microservice.mission.entities.Waste
import swc.microservice.mission.usecases.CompleteMissionStep
import swc.microservice.mission.usecases.MissionManager

class MissionStepEvent<T : Waste>(private val missionId: String) : MissionEvent<Mission<T>> {
    override fun handle(manager: MissionManager): Mission<T> = CompleteMissionStep<T>(this.missionId).execute(manager)
}
