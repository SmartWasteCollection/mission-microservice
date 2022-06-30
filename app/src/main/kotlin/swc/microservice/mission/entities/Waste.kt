package swc.microservice.mission.entities

import kotlinx.serialization.Serializable

sealed interface Waste

@Serializable
enum class OrdinaryWaste : Waste {
    UNSORTED, PLASTICS_ALUMINIUM, ORGANIC, GLASS, PAPER
}

@Serializable
enum class ExtraordinaryWaste : Waste {
    TWIGS, WASTE_OIL, IRON, ELECTRONICS, CLOTHES, OTHER
}
