package swc.microservice.mission.entities

import kotlinx.serialization.Serializable

@Serializable
data class TypeOfWaste<T : Waste>(val wasteName: T)
