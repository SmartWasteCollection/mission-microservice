package swc.microservice.mission.entities

data class Booking<out T : Waste>(
    val _id: String,
    val typeOfWaste: TypeOfWaste<T>,
    val position: Position,
    val status: BookingStatus = BookingStatus.PENDING
)

enum class BookingStatus {
    PENDING, REQUESTED, FULFILLED
}
