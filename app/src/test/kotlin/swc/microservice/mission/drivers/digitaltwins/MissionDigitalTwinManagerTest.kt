package swc.microservice.mission.drivers.digitaltwins

import com.azure.digitaltwins.core.BasicDigitalTwin
import com.azure.digitaltwins.core.BasicDigitalTwinMetadata
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import swc.microservice.mission.entities.Mission
import swc.microservice.mission.entities.MissionStep
import swc.microservice.mission.entities.OrdinaryWaste
import swc.microservice.mission.entities.Position
import swc.microservice.mission.entities.TypeOfMission
import swc.microservice.mission.entities.TypeOfWaste
import swc.microservice.mission.entities.WasteColor

class MissionDigitalTwinManagerTest : FreeSpec({
    val manager = MissionDigitalTwinManager()

    val missionId = "MissionTest${System.currentTimeMillis()}"
    val id = "CollectionPointTest${System.currentTimeMillis()}"
    val truckId = "TruckTest${System.currentTimeMillis()}"

    val collectionPoint = BasicDigitalTwin(id)
        .setMetadata(BasicDigitalTwinMetadata().setModelId("dtmi:swc:CollectionPoint;1"))
        .addToContents("position", Position(0.0, 0.0))
    val truck = BasicDigitalTwin(truckId)
        .setMetadata(BasicDigitalTwinMetadata().setModelId("dtmi:swc:Truck;1"))
        .addToContents("position", Position(0.0, 0.0))
        .addToContents("occupiedVolume", "{ \"value\": 0 }")
        .addToContents("capacity", 0)
        .addToContents("inMission", false)
    val mission = Mission(
        missionId = missionId,
        truckId = null,
        typeOfWaste = TypeOfWaste(OrdinaryWaste.PAPER, WasteColor.BLUE),
        typeOfMission = TypeOfMission.ORDINARY,
        missionSteps = listOf(MissionStep(id))
    )

    "The mission manager" - {
        "when communicating with Azure Digital Twins" - {
            "should create a digital twin with its relationships" {
                manager.createDigitalTwin(truck).id shouldBe truckId
                manager.createDigitalTwin(collectionPoint).id shouldBe id
                manager.createMission(mission) shouldBe missionId
            }
            "should read digital twins" {
                shouldNotThrow<Exception> {
                    manager.getMissionById(missionId)
                }
            }
            "should complete steps" {
                mission.isCompleted() shouldBe false
                val completedMission = manager.completeMissionStep(mission.missionId)
                completedMission?.isCompleted() shouldBe true
            }
            "should assign missions to trucks" {
                manager.assignTruckToMission(mission.missionId, truckId)?.truckId shouldNotBe null
            }
            "should delete digital twins" {
                val toDelete = manager.getMissionById(mission.missionId)
                manager.deleteMission(missionId) shouldBe toDelete
                shouldNotThrow<Exception> {
                    manager.deleteDigitalTwin(truckId)
                    manager.deleteDigitalTwin(id)
                }
            }
        }
    }
})
