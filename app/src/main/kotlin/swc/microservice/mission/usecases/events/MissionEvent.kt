package swc.microservice.mission.usecases.events

import swc.microservice.mission.usecases.MissionManager

interface MissionEvent<T> {
    fun handle(manager: MissionManager): T
}
