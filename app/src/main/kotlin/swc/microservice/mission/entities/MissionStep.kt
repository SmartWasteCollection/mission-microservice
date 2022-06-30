package swc.microservice.mission.entities

import kotlinx.serialization.Serializable

@Serializable
data class MissionStep(val position: Position, val completed: Boolean = false)
