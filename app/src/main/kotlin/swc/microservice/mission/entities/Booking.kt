package swc.microservice.mission.entities

data class Booking(
    val id: String,
    val typeOfWaste: TypeOfWaste,
    val position: Position
)
