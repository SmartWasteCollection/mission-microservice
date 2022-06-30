package swc.microservice.mission.entities

import kotlinx.serialization.Serializable

@Serializable
data class Booking<T : Waste>(
    val id: String,
    val typeOfWaste: TypeOfWaste<T>,
    val position: Position,
    val status: BookingStatus = BookingStatus.PENDING
)

@Serializable
enum class BookingStatus {
    PENDING, REQUESTED, FULFILLED
}
