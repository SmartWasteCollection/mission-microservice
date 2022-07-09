package swc.microservice.mission.drivers

import swc.microservice.mission.drivers.digitaltwins.MissionDigitalTwinManager
import swc.microservice.mission.usecases.managers.MissionManager

class ManagerSupplierImpl {
    fun mission(): MissionManager = MissionDigitalTwinManager()
}
