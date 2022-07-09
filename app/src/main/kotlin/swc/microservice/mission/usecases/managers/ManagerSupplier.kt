package swc.microservice.mission.usecases.managers

interface ManagerSupplier {
    fun mission(): MissionManager

    fun dumpster(): DumpsterManager

    fun booking(): BookingManager
}
