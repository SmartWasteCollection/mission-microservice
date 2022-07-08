package swc.microservice.mission.entities

import java.time.LocalDate

data class Booking(
    val _id: String,
    val userId: String,
    val typeOfWaste: TypeOfWaste<ExtraordinaryWaste>,
    val datetime: LocalDate,
    val city: String,
    val province: String,
    val address: String,
    val status: BookingStatus = BookingStatus.PENDING
)

enum class BookingStatus {
    PENDING, ASSIGNED, COMPLETED
}
