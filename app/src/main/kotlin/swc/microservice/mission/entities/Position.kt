package swc.microservice.mission.entities

import kotlinx.serialization.Serializable

@Serializable
data class Position(val latitude: Long, val longitude: Long)
