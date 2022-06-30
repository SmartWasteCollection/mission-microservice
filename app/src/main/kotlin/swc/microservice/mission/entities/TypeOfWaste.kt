package swc.microservice.mission.entities

data class TypeOfWaste<T : Waste>(val wasteName: T)
