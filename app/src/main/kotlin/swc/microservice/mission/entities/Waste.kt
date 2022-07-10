package swc.microservice.mission.entities

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import swc.microservice.mission.adapters.MissionPresentation

@JsonDeserialize(using = MissionPresentation.Deserialization.WasteDeserializer::class)
sealed interface Waste

enum class OrdinaryWaste : Waste {
    UNSORTED, PLASTICS_ALUMINIUM, ORGANIC, GLASS, PAPER
}

enum class ExtraordinaryWaste : Waste {
    TWIGS, WASTE_OIL, IRON, ELECTRONICS, CLOTHES, OTHER
}
