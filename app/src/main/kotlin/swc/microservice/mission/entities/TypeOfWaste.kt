package swc.microservice.mission.entities

import kotlinx.serialization.Serializable

@Serializable
data class TypeOfWaste<T : Waste>(val wasteName: T)

fun String.toTypeOfWaste(): TypeOfWaste<ExtraordinaryWaste> = TypeOfWaste(
    when (this.uppercase()) {
        "ELECTRONICS" -> ExtraordinaryWaste.ELECTRONICS
        "IRON" -> ExtraordinaryWaste.IRON
        "CLOTHES" -> ExtraordinaryWaste.CLOTHES
        "TWIGS" -> ExtraordinaryWaste.TWIGS
        "WASTE_OIL" -> ExtraordinaryWaste.WASTE_OIL
        else -> ExtraordinaryWaste.OTHER
    }
)
