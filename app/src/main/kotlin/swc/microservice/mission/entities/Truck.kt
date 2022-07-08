package swc.microservice.mission.entities

data class Truck(
    val id: String,
    val position: Position,
    val occupiedVolume: Volume,
    val capacity: Double,
    val isInMission: Boolean = false
)
