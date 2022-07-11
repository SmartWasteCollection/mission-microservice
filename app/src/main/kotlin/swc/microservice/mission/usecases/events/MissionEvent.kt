package swc.microservice.mission.usecases.events

import swc.microservice.mission.usecases.managers.ManagerSupplier

interface MissionEvent<T> {
    fun handle(manager: ManagerSupplier): T
}
