package swc.microservice.mission.usecases.events

import swc.microservice.mission.entities.ExtraordinaryWaste
import swc.microservice.mission.entities.TypeOfWaste
import swc.microservice.mission.usecases.ComputeExtraordinaryMission
import swc.microservice.mission.usecases.managers.ManagerSupplier

class ExtraordinaryMissionEvent(private val typeOfWaste: TypeOfWaste<ExtraordinaryWaste>) :
    MissionEvent<String> {
    override fun handle(manager: ManagerSupplier): String =
        ComputeExtraordinaryMission(this.typeOfWaste).execute(manager)
}
