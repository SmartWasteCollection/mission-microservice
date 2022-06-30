package swc.microservice.mission.entities

sealed interface Waste

enum class OrdinaryWaste : Waste {
    UNSORTED, PLASTICS_ALUMINIUM, ORGANIC, GLASS, PAPER
}

enum class ExtraordinaryWaste : Waste {
    TWIGS, WASTE_OIL, IRON, ELECTRONICS, CLOTHES, OTHER
}