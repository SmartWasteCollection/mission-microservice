package swc.microservice.mission.usecases.events

import swc.microservice.mission.usecases.managers.MissionManager

interface MissionEvent<T> {
    fun handle(manager: MissionManager): T
}
