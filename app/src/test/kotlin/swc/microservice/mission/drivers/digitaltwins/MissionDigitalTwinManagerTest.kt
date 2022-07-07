package swc.microservice.mission.drivers.digitaltwins

import io.kotest.core.spec.style.FreeSpec
import swc.microservice.mission.entities.Mission
import swc.microservice.mission.entities.MissionStep
import swc.microservice.mission.entities.OrdinaryWaste
import swc.microservice.mission.entities.Position
import swc.microservice.mission.entities.TypeOfMission
import swc.microservice.mission.entities.TypeOfWaste

class MissionDigitalTwinManagerTest : FreeSpec({
    // val id = "MissionTest${System.currentTimeMillis()}"
    val id = "MissionTest"
    val manager = MissionDigitalTwinManager()

    "The mission manager" - {
        "when communicating with Azure Digital Twins" - {
            "should create a digital twin" {
                val mission = Mission(
                    missionId = id,
                    truckId = null,
                    typeOfWaste = TypeOfWaste(OrdinaryWaste.PAPER),
                    typeOfMission = TypeOfMission.ORDINARY,
                    missionSteps = listOf(MissionStep("CollectionPoint0", Position(0L, 0L)))
                )
                val collectionPoint = Mission(
                    missionId = "CollectionPointTestIncredibleFake",
                    truckId = null,
                    typeOfWaste = TypeOfWaste(OrdinaryWaste.PAPER),
                    typeOfMission = TypeOfMission.ORDINARY,
                    missionSteps = listOf()
                )
                manager.createMission(collectionPoint)
                manager.createMission(mission)
            }
            "should read digital twins" {
                manager.getMission(id)
            }
            "should delete digital twins" {
                manager.deleteMission(id)
            }
        }
    }
})
