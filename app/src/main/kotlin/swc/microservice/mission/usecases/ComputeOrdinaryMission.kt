package swc.microservice.mission.usecases

import swc.microservice.mission.entities.Mission
import swc.microservice.mission.entities.OrdinaryWaste
import swc.microservice.mission.usecases.managers.MissionManager

class ComputeOrdinaryMission(private val dumpsterId: String?) : MissionUseCase<Mission<OrdinaryWaste>> {
    override fun execute(manager: MissionManager): Mission<OrdinaryWaste> =
        manager.computeOrdinaryMission(this.dumpsterId)
}
