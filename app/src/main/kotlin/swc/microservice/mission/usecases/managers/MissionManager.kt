package swc.microservice.mission.usecases.managers

import swc.microservice.mission.entities.Mission
import swc.microservice.mission.entities.Waste

interface MissionManager {
    fun completeMissionStep(missionId: String): Mission<Waste>?

    fun getMissions(): List<Mission<Waste>>

    fun getMissionById(missionId: String): Mission<Waste>?

    fun assignTruckToMission(missionId: String, truckId: String): Mission<Waste>?

    fun createMission(mission: Mission<Waste>): String

    fun createNewMissionId(): String
}
