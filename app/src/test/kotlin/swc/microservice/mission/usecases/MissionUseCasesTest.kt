package swc.microservice.mission.usecases

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import swc.microservice.mission.entities.ExtraordinaryWaste
import swc.microservice.mission.entities.Mission
import swc.microservice.mission.entities.MissionStep
import swc.microservice.mission.entities.OrdinaryWaste
import swc.microservice.mission.entities.Position
import swc.microservice.mission.entities.TypeOfMission
import swc.microservice.mission.entities.TypeOfWaste
import swc.microservice.mission.entities.Waste

class MissionUseCasesTest : FreeSpec({
    var missions: List<Mission<Waste>> = listOf()
    val collectionPointId = "collectionPoint"
    val position = Position(0L, 0L)
    val ordinaryMission = Mission(
        missionId = "mission0",
        typeOfWaste = TypeOfWaste(OrdinaryWaste.PAPER),
        typeOfMission = TypeOfMission.ORDINARY,
        missionSteps = listOf(MissionStep(collectionPointId, position))
    )
    val typeOfWaste = TypeOfWaste(ExtraordinaryWaste.ELECTRONICS)

    fun extraordinaryMission(
        typeOfWaste: TypeOfWaste<ExtraordinaryWaste>,
        missionSteps: List<MissionStep> = listOf(MissionStep(collectionPointId, position))
    ): Mission<ExtraordinaryWaste> = Mission(
        missionId = "mission1",
        typeOfWaste = TypeOfWaste(typeOfWaste.wasteName),
        typeOfMission = TypeOfMission.EXTRAORDINARY,
        missionSteps = missionSteps
    )

    val manager: MissionManager = object : MissionManager {
        override fun computeOrdinaryMission(dumpsterId: String?): Mission<OrdinaryWaste> =
            ordinaryMission.also { missions = missions + it }

        override fun computeExtraordinaryMission(typeOfWaste: TypeOfWaste<ExtraordinaryWaste>): Mission<ExtraordinaryWaste> =
            extraordinaryMission(typeOfWaste).also { missions = missions + it }

        override fun completeMissionStep(missionId: String): Mission<Waste>? {
            missions = missions.map {
                when (it.missionId) {
                    missionId -> extraordinaryMission(typeOfWaste, listOf(MissionStep(collectionPointId, position, true)))
                    else -> it
                }
            }
            return missions.find { it.missionId == missionId }
        }
    }

    "When performing the mission use cases" - {
        "The ComputeOrdinaryMission use case" - {
            "should create a mission" {
                ComputeOrdinaryMission(null).execute(manager) shouldBe ordinaryMission
                missions.size shouldBe 1
                assert(missions.contains(ordinaryMission))
                missions.find { it == ordinaryMission }?.typeOfMission shouldBe TypeOfMission.ORDINARY
            }
        }
        "The ComputeExtraordinaryMission use case" - {
            "should create a mission" {
                ComputeExtraordinaryMission(typeOfWaste).execute(manager) shouldBe extraordinaryMission(typeOfWaste)
                missions.size shouldBe 2
                assert(missions.contains(extraordinaryMission(typeOfWaste)))
                missions.find { it == extraordinaryMission(typeOfWaste) }?.typeOfMission shouldBe TypeOfMission.EXTRAORDINARY
                missions.find { it == extraordinaryMission(typeOfWaste) }?.typeOfWaste shouldBe typeOfWaste
            }
        }
        "The CompleteMissionStep use case" - {
            "should complete a step of the mission" {
                val missionId = extraordinaryMission(typeOfWaste).missionId
                CompleteMissionStep(missionId).execute(
                    manager
                ) shouldBe extraordinaryMission(typeOfWaste, listOf(MissionStep(collectionPointId, position, true)))
                missions.size shouldBe 2
                missions.find { it.missionId == missionId }?.typeOfMission shouldBe TypeOfMission.EXTRAORDINARY
                missions.find { it.missionId == missionId }?.typeOfWaste shouldBe typeOfWaste
                assert(missions.find { it.missionId == missionId }?.isCompleted() ?: false)
            }
        }
    }
})
