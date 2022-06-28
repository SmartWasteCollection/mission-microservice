package swc.microservice.mission.entities

interface WasteName

enum class OrdinaryWasteName : WasteName {
    UNSORTED, PLASTICS_ALUMINIUM, ORGANIC, GLASS, PAPER
}

enum class ExtraordinaryWasteName : WasteName {
    TWIGS, WASTE_OIL, IRON, ELECTRONICS, CLOTHES, OTHER
}