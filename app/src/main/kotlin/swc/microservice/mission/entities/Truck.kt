package swc.microservice.mission.entities

data class Truck(
    val truckId: String,
    val position: Position,
    val occupiedVolume: Volume,
    val capacity: Double,
    val inMission: Boolean = false
)
