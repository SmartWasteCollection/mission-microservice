package swc.microservice.mission.usecases.events

import swc.microservice.mission.usecases.ComputeOrdinaryMission
import swc.microservice.mission.usecases.managers.ManagerSupplier

class MissionRequestEvent(private val dumpsterId: String) : MissionEvent<String> {
    override fun handle(manager: ManagerSupplier): String =
        ComputeOrdinaryMission(this.dumpsterId).execute(manager)
}
