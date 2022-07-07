package swc.microservice.mission.entities

data class Booking<out T : Waste>(
    val bookingId: String,
    val typeOfWaste: TypeOfWaste<T>,
    val position: Position,
    val status: BookingStatus = BookingStatus.PENDING
)

enum class BookingStatus {
    PENDING, ASSIGNED, COMPLETED
}
