package swc.microservice.mission.usecases.events

import swc.microservice.mission.entities.ExtraordinaryWaste
import swc.microservice.mission.entities.Mission
import swc.microservice.mission.entities.TypeOfWaste
import swc.microservice.mission.usecases.ComputeExtraordinaryMission
import swc.microservice.mission.usecases.MissionManager

class ExtraordinaryMissionEvent(private val typeOfWaste: TypeOfWaste<ExtraordinaryWaste>) :
    MissionEvent<Mission<ExtraordinaryWaste>> {
    override fun handle(manager: MissionManager): Mission<ExtraordinaryWaste> =
        ComputeExtraordinaryMission(this.typeOfWaste).execute(manager)
}
