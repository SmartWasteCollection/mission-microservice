package swc.microservice.mission.usecases

import swc.microservice.mission.usecases.managers.ManagerSupplier

interface MissionUseCase<T> {
    fun execute(manager: ManagerSupplier): T
}
