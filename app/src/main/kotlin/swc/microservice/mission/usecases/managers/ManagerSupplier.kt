package swc.microservice.mission.usecases.managers

import swc.microservice.mission.entities.Mission
import swc.microservice.mission.entities.TypeOfMission
import swc.microservice.mission.entities.Waste

interface ManagerSupplier {
    fun mission(type: TypeOfMission): MissionManager

    fun mission(mission: Mission<Waste>): MissionManager

    fun dumpster(): DumpsterManager

    fun booking(): BookingManager
}
