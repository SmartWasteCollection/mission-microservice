package swc.microservice.mission.usecases

import swc.microservice.mission.entities.ExtraordinaryWaste
import swc.microservice.mission.entities.Mission
import swc.microservice.mission.entities.TypeOfWaste
import swc.microservice.mission.usecases.managers.MissionManager

class ComputeExtraordinaryMission(private val typeOfWaste: TypeOfWaste<ExtraordinaryWaste>) :
    MissionUseCase<Mission<ExtraordinaryWaste>> {
    override fun execute(manager: MissionManager): Mission<ExtraordinaryWaste> =
        manager.computeExtraordinaryMission(this.typeOfWaste)
}
