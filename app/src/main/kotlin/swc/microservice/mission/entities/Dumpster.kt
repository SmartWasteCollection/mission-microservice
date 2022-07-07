package swc.microservice.mission.entities

import kotlinx.serialization.Serializable

private const val MAX_VOLUME_THRESHOLD: Double = 95.0
const val TIMEOUT_SECONDS: Double = 30.0

@Serializable
data class Dumpster(
    val _id: String,
    val collectionPoint: String,
    val dumpsterType: TypeOfDumpster,
    var isOpen: Boolean = false,
    val occupiedVolume: Volume,
    val isWorking: Boolean = true,
) {
    fun isAvailable(): Boolean =
        this.isWorking && this.occupiedVolume
            .getOccupiedPercentage(this.dumpsterType.size.capacity) < MAX_VOLUME_THRESHOLD
}

@Serializable
data class TypeOfDumpster(
    val size: Size,
    val typeOfWaste: TypeOfWaste<OrdinaryWaste>
)

@Serializable
data class Size(
    val dimension: Dimension,
    val capacity: Double,
)

@Serializable
enum class Dimension {
    SMALL, LARGE
}

@Serializable
data class Volume(val value: Double) {
    fun getOccupiedPercentage(capacity: Double): Double =
        value / capacity * 100
}
