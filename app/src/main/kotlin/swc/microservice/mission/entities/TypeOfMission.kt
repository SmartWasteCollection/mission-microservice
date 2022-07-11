package swc.microservice.mission.entities

enum class TypeOfMission {
    ORDINARY, EXTRAORDINARY
}

fun String.toTypeOfMission(): TypeOfMission = when (this) {
    "ORDINARY" -> TypeOfMission.ORDINARY
    else -> TypeOfMission.EXTRAORDINARY
}
