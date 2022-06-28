package swc.microservice.mission.usecases

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import swc.microservice.mission.entities.Booking
import swc.microservice.mission.entities.ExtraordinaryWasteName
import swc.microservice.mission.entities.TypeOfMission
import swc.microservice.mission.entities.TypeOfWaste
import kotlin.reflect.KSuspendFunction1

class MissionManagerTest : FreeSpec({
    val typeOfWaste = TypeOfWaste(ExtraordinaryWasteName.ELECTRONICS)
    suspend fun bookingsSupplier(typeOfWaste: TypeOfWaste): List<Booking> =
        listOf()
    fun bookingsRetriever(): KSuspendFunction1<TypeOfWaste, List<Booking>> = ::bookingsSupplier

    "The mission manager" - {
        "when computing an extraordinary mission" - {
            "should produce a proper mission" {
                val mission = computeExtraordinaryMission(typeOfWaste, ::bookingsRetriever)
                mission.typeOfMission shouldBe TypeOfMission.EXTRAORDINARY
                mission.typeOfWaste shouldBe typeOfWaste
                mission.missionSteps.forEach { it.completed shouldBe false }
            }
        }
    }
})