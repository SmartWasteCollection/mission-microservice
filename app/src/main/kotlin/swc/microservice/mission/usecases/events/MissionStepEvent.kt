package swc.microservice.mission.usecases.events

import swc.microservice.mission.entities.Mission
import swc.microservice.mission.entities.Waste
import swc.microservice.mission.usecases.CompleteMissionStep
import swc.microservice.mission.usecases.managers.MissionManager

class MissionStepEvent(private val missionId: String) : MissionEvent<Mission<Waste>?> {
    override fun handle(manager: MissionManager): Mission<Waste>? = CompleteMissionStep(this.missionId).execute(manager)
}
