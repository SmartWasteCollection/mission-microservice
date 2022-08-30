package swc.microservice.mission.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

private const val MAX_VOLUME_THRESHOLD: Double = 95.0
const val TIMEOUT_SECONDS: Double = 30.0

@JsonIgnoreProperties(ignoreUnknown = true)
data class Dumpster(
    val id: String,
    val dumpsterType: TypeOfDumpster,
    var open: Boolean = false,
    val occupiedVolume: Volume,
    val working: Boolean = true,
) {
    fun available(): Boolean =
        this.working && this.occupiedVolume
            .getOccupiedPercentage(this.dumpsterType.size.capacity) < MAX_VOLUME_THRESHOLD
}

data class TypeOfDumpster(
    val size: Size,
    val typeOfOrdinaryWaste: TypeOfWaste<OrdinaryWaste>
)

data class Size(
    val dimension: Dimension,
    val capacity: Double,
)

enum class Dimension {
    SMALL, LARGE
}

data class Volume(val value: Double) {
    fun getOccupiedPercentage(capacity: Double): Double =
        value / capacity * 100
}
