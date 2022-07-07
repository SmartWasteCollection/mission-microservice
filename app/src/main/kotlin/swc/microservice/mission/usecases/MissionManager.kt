package swc.microservice.mission.usecases

import swc.microservice.mission.entities.ExtraordinaryWaste
import swc.microservice.mission.entities.Mission
import swc.microservice.mission.entities.OrdinaryWaste
import swc.microservice.mission.entities.TypeOfWaste
import swc.microservice.mission.entities.Waste

interface MissionManager {
    fun computeOrdinaryMission(dumpsterId: String): Mission<OrdinaryWaste>

    fun computeExtraordinaryMission(typeOfWaste: TypeOfWaste<ExtraordinaryWaste>): Mission<ExtraordinaryWaste>

    fun <T : Waste> completeMissionStep(missionId: String): Mission<T>
}
