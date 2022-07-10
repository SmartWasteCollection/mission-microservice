package swc.microservice.mission.drivers

import swc.microservice.mission.drivers.database.MissionDatabaseManager
import swc.microservice.mission.drivers.digitaltwins.MissionDigitalTwinManager
import swc.microservice.mission.drivers.http.HttpBookingManager
import swc.microservice.mission.drivers.http.HttpDumpsterManager
import swc.microservice.mission.entities.TypeOfMission
import swc.microservice.mission.usecases.managers.BookingManager
import swc.microservice.mission.usecases.managers.DumpsterManager
import swc.microservice.mission.usecases.managers.ManagerSupplier
import swc.microservice.mission.usecases.managers.MissionManager

class ManagerSupplierImpl : ManagerSupplier {
    override fun mission(type: TypeOfMission): MissionManager = when (type) {
        TypeOfMission.ORDINARY -> MissionDigitalTwinManager()
        TypeOfMission.EXTRAORDINARY -> MissionDatabaseManager()
    }

    override fun dumpster(): DumpsterManager = HttpDumpsterManager()

    override fun booking(): BookingManager = HttpBookingManager()
}
