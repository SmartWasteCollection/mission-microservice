package swc.microservice.mission.usecases

import swc.microservice.mission.entities.ExtraordinaryWaste
import swc.microservice.mission.entities.Mission
import swc.microservice.mission.entities.MissionStep
import swc.microservice.mission.entities.TypeOfMission
import swc.microservice.mission.entities.TypeOfWaste
import swc.microservice.mission.usecases.managers.ManagerSupplier

class ComputeExtraordinaryMission(private val typeOfWaste: TypeOfWaste<ExtraordinaryWaste>) :
    MissionUseCase<String> {
    override fun execute(manager: ManagerSupplier): String =
        manager.booking().getBookings().filter { it.typeOfWaste == this.typeOfWaste }.let {
            manager.mission(TypeOfMission.EXTRAORDINARY).createMission(
                Mission(
                    missionId = manager.mission(TypeOfMission.EXTRAORDINARY).createNewMissionId(),
                    typeOfWaste = this.typeOfWaste,
                    typeOfMission = TypeOfMission.EXTRAORDINARY,
                    missionSteps = it.map { b -> MissionStep(b._id) }
                )
            )
        }
}
