package swc.microservice.mission.entities

import kotlinx.serialization.Serializable

@Serializable
enum class TypeOfMission {
    ORDINARY, EXTRAORDINARY
}
