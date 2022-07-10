package swc.microservice.mission.usecases

import swc.microservice.mission.entities.Mission
import swc.microservice.mission.entities.MissionStep
import swc.microservice.mission.entities.TypeOfMission
import swc.microservice.mission.usecases.managers.ManagerSupplier

class ComputeOrdinaryMission(private val dumpsterId: String?) : MissionUseCase<String> {
    override fun execute(manager: ManagerSupplier): String =
        manager.dumpster().getDumpsters().let { dumpsters ->
            dumpsters
                .filter { d -> d.dumpsterType.typeOfWaste == dumpsters.find { du -> du.id == dumpsterId }?.dumpsterType?.typeOfWaste }
                .filter { d -> d.occupiedVolume.getOccupiedPercentage(d.dumpsterType.size.capacity) >= 50 }.take(10)
                .let {
                    manager.mission(TypeOfMission.ORDINARY).createMission(
                        Mission(
                            missionId = manager.mission(TypeOfMission.ORDINARY).createNewMissionId(),
                            typeOfWaste = it.first().dumpsterType.typeOfWaste,
                            typeOfMission = TypeOfMission.ORDINARY,
                            missionSteps = it.map { du -> manager.dumpster().getCollectionPoint(du.id) }
                                .map { cp -> MissionStep(cp.collectionPointId) }
                        )
                    )
                }
        }
}
