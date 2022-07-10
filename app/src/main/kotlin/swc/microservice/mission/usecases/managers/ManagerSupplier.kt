package swc.microservice.mission.usecases.managers

import swc.microservice.mission.entities.TypeOfMission

interface ManagerSupplier {
    fun mission(type: TypeOfMission): MissionManager

    fun dumpster(): DumpsterManager

    fun booking(): BookingManager
}
