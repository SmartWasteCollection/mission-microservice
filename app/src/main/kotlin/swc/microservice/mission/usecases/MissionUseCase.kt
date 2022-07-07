package swc.microservice.mission.usecases

import swc.microservice.mission.usecases.managers.MissionManager

interface MissionUseCase<T> {
    fun execute(manager: MissionManager): T
}
