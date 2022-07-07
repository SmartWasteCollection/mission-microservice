package swc.microservice.mission.usecases

interface MissionUseCase<T> {
    fun execute(manager: MissionManager): T
}
