package swc.microservice.mission.usecases

interface MissionUseCase<T> {
    fun execute(missionManager: MissionManager): T
}
