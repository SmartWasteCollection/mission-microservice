package swc.microservice.mission.usecases.managers

import swc.microservice.mission.entities.ExtraordinaryWaste
import swc.microservice.mission.entities.Mission
import swc.microservice.mission.entities.OrdinaryWaste
import swc.microservice.mission.entities.TypeOfWaste
import swc.microservice.mission.entities.Waste

interface MissionManager {
    fun computeOrdinaryMission(dumpsterId: String?): Mission<OrdinaryWaste>

    fun computeExtraordinaryMission(typeOfWaste: TypeOfWaste<ExtraordinaryWaste>): Mission<ExtraordinaryWaste>

    fun completeMissionStep(missionId: String): Mission<Waste>?

    fun getMissions(): List<Mission<Waste>>

    fun getMissionById(missionId: String): Mission<Waste>?

    fun updateMission(mission: Mission<Waste>): Mission<Waste>?
}
