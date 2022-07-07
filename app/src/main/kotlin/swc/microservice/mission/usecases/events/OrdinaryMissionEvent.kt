package swc.microservice.mission.usecases.events

import swc.microservice.mission.entities.Mission
import swc.microservice.mission.entities.OrdinaryWaste
import swc.microservice.mission.usecases.ComputeOrdinaryMission
import swc.microservice.mission.usecases.MissionManager

class OrdinaryMissionEvent(private val dumpsterId: String?) : MissionEvent<Mission<OrdinaryWaste>> {
    override fun handle(manager: MissionManager): Mission<OrdinaryWaste> =
        ComputeOrdinaryMission(this.dumpsterId).execute(manager)
}
