package swc.microservice.mission.usecases

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.ints.shouldBeLessThan
import io.kotest.matchers.shouldBe
import swc.microservice.mission.entities.*

class MissionManagerTest : FreeSpec({
    val typeOfWaste = TypeOfWaste(ExtraordinaryWaste.ELECTRONICS)
    val position = Position(42, 42)
    val bookings = listOf(
        Booking("1", typeOfWaste, position),
        Booking("2", typeOfWaste, position),
        Booking("3", typeOfWaste, position),
        Booking("4", typeOfWaste, position),
        Booking("5", TypeOfWaste(ExtraordinaryWaste.CLOTHES), position),
        Booking("6", TypeOfWaste(ExtraordinaryWaste.IRON), position),
    )
    fun bookingsSupplier(typeOfWaste: TypeOfWaste<ExtraordinaryWaste>): List<Booking<ExtraordinaryWaste>> =
        bookings.filter { it.typeOfWaste == typeOfWaste }

    "The mission manager" - {
        "when computing an extraordinary mission" - {
            val mission = computeExtraordinaryMission(typeOfWaste) { bookingsSupplier(it) }
            "should produce the correct type of mission" {
                mission.typeOfMission shouldBe TypeOfMission.EXTRAORDINARY
                mission.typeOfWaste shouldBe typeOfWaste
            }
            "the steps should contain the specified type of waste" - {
                mission.missionSteps.size shouldBeLessThan MAX_EXTRAORDINARY_MISSION_STEPS
                mission.missionSteps.size shouldBe bookings
                    .filter { it.typeOfWaste == typeOfWaste }
                    .take(MAX_EXTRAORDINARY_MISSION_STEPS).size
                mission.missionSteps.forEach { it.completed shouldBe false }
            }
        }
    }
})