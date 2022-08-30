package swc.microservice.mission.entities

data class TypeOfWaste<out T : Waste>(val wasteName: T, val wasteColor: WasteColor)

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
    },
    fromWaste(this.uppercase())
)

fun String.toExtraordinaryTypeOfWaste(): TypeOfWaste<ExtraordinaryWaste> = TypeOfWaste(
    when (this.uppercase()) {
        "ELECTRONICS" -> ExtraordinaryWaste.ELECTRONICS
        "IRON" -> ExtraordinaryWaste.IRON
        "CLOTHES" -> ExtraordinaryWaste.CLOTHES
        "TWIGS" -> ExtraordinaryWaste.TWIGS
        "WASTE_OIL" -> ExtraordinaryWaste.WASTE_OIL
        else -> ExtraordinaryWaste.OTHER
    },
    WasteColor.NONE
)

enum class WasteColor {
    GREEN, YELLOW, BLUE, BROWN, GREY, NONE
}

fun fromWaste(waste: String): WasteColor = when (waste) {
    "PAPER" -> WasteColor.BLUE
    "UNSORTED" -> WasteColor.GREY
    "ORGANIC" -> WasteColor.BROWN
    "GLASS" -> WasteColor.GREEN
    "PLASTICS_ALUMINIUM" -> WasteColor.YELLOW
    else -> WasteColor.NONE
}
