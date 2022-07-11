package swc.microservice.mission.entities

data class TypeOfWaste<out T : Waste>(val wasteName: T)

fun String.toTypeOfWaste(): TypeOfWaste<Waste> = when (this) {
    "PAPER", "PLASTICS/ALUMINIUM", "ORGANIC", "GLASS", "UNSORTED" -> this.toOrdinaryTypeOfWaste()
    else -> this.toExtraordinaryTypeOfWaste()
}

fun String.toOrdinaryTypeOfWaste(): TypeOfWaste<OrdinaryWaste> = TypeOfWaste(
    when (this.uppercase()) {
        "PAPER" -> OrdinaryWaste.PAPER
        "PLASTICS/ALUMINIUM" -> OrdinaryWaste.PLASTICS_ALUMINIUM
        "ORGANIC" -> OrdinaryWaste.ORGANIC
        "GLASS" -> OrdinaryWaste.GLASS
        else -> OrdinaryWaste.UNSORTED
    }
)

fun String.toExtraordinaryTypeOfWaste(): TypeOfWaste<ExtraordinaryWaste> = TypeOfWaste(
    when (this.uppercase()) {
        "ELECTRONICS" -> ExtraordinaryWaste.ELECTRONICS
        "IRON" -> ExtraordinaryWaste.IRON
        "CLOTHES" -> ExtraordinaryWaste.CLOTHES
        "TWIGS" -> ExtraordinaryWaste.TWIGS
        "WASTE_OIL" -> ExtraordinaryWaste.WASTE_OIL
        else -> ExtraordinaryWaste.OTHER
    }
)
