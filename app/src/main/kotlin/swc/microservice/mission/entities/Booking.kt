package swc.microservice.mission.entities

import kotlinx.serialization.Serializable

@Serializable
data class Booking(
    val id: String,
    val typeOfWaste: TypeOfWaste,
    val position: Position
)
