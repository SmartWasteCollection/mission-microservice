package swc.microservice.mission.usecases

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.ints.shouldBeLessThan
import io.kotest.matchers.shouldBe
import swc.microservice.mission.entities.*

class MissionManagerTest : FreeSpec({
    val typeOfWaste = TypeOfWaste(ExtraordinaryWaste.ELECTRONICS)
    val position = Position(42, 42)
    var dummyBookings = listOf(
        Booking("1", typeOfWaste, position),
        Booking("2", typeOfWaste, position),
        Booking("3", typeOfWaste, position),
        Booking("4", typeOfWaste, position),
        Booking("5", TypeOfWaste(ExtraordinaryWaste.CLOTHES), position),
        Booking("6", TypeOfWaste(ExtraordinaryWaste.IRON), position),
    )
    fun bookingsSupplier(typeOfWaste: TypeOfWaste<ExtraordinaryWaste>): List<Booking<ExtraordinaryWaste>> =
        dummyBookings.filter { it.typeOfWaste == typeOfWaste }
    fun bookingsUpdater(bookings: List<Booking<ExtraordinaryWaste>>) {
        dummyBookings = bookings.map { Booking(it.id, it.typeOfWaste, it.position, BookingStatus.REQUESTED) }
    }

    "The mission manager" - {
        "when computing an extraordinary mission" - {
            val mission = computeExtraordinaryMission(typeOfWaste, ::bookingsSupplier) { bookingsUpdater(it) }
            "should produce the correct type of mission" {
                mission.typeOfMission shouldBe TypeOfMission.EXTRAORDINARY
                mission.typeOfWaste shouldBe typeOfWaste
            }
            "the steps should contain the specified type of waste" {
                mission.missionSteps.size shouldBeLessThan MAX_EXTRAORDINARY_MISSION_STEPS
                mission.missionSteps.size shouldBe dummyBookings
                    .filter { it.typeOfWaste == typeOfWaste }
                    .take(MAX_EXTRAORDINARY_MISSION_STEPS).size
                mission.missionSteps.forEach { it.completed shouldBe false }
            }
            "the bookings inserted in the mission should be updated" {
                dummyBookings.forEach { it.status shouldBe BookingStatus.REQUESTED }
            }
        }
    }
})