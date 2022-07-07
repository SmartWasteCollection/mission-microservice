package swc.microservice.mission.entities

data class Booking(
    val bookingId: String,
    val typeOfWaste: TypeOfWaste<ExtraordinaryWaste>,
    val position: Position,
    val status: BookingStatus = BookingStatus.PENDING
)

enum class BookingStatus {
    PENDING, ASSIGNED, COMPLETED
}
