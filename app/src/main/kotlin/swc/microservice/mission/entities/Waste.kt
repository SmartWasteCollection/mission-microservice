package swc.microservice.mission.entities

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonSubTypes.Type
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes(
    Type(value = OrdinaryWaste::class, name = "OrdinaryWaste"),
    Type(value = ExtraordinaryWaste::class, name = "ExtraordinaryWaste")
)
sealed interface Waste

enum class OrdinaryWaste : Waste {
    UNSORTED, PLASTICS_ALUMINIUM, ORGANIC, GLASS, PAPER
}

enum class ExtraordinaryWaste : Waste {
    TWIGS, WASTE_OIL, IRON, ELECTRONICS, CLOTHES, OTHER
}
