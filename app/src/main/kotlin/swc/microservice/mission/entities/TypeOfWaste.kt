package swc.microservice.mission.entities

data class TypeOfWaste<out T : Waste>(val wasteName: T)

fun String.toTypeOfWaste(): TypeOfWaste<*> = TypeOfWaste(
    when (this.uppercase()) {
        "PAPER" -> OrdinaryWaste.PAPER
        "PLASTICS/ALUMINIUM" -> OrdinaryWaste.PLASTICS_ALUMINIUM
        "ORGANIC" -> OrdinaryWaste.ORGANIC
        "GLASS" -> OrdinaryWaste.GLASS
        "UNSORTED" -> OrdinaryWaste.UNSORTED
        "ELECTRONICS" -> ExtraordinaryWaste.ELECTRONICS
        "IRON" -> ExtraordinaryWaste.IRON
        "CLOTHES" -> ExtraordinaryWaste.CLOTHES
        "TWIGS" -> ExtraordinaryWaste.TWIGS
        "WASTE_OIL" -> ExtraordinaryWaste.WASTE_OIL
        else -> ExtraordinaryWaste.OTHER
    }
)
