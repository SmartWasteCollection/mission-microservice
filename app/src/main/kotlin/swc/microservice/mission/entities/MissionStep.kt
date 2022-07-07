package swc.microservice.mission.entities

data class MissionStep(val collectionPointId: String, val position: Position, val completed: Boolean = false)
