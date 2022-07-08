package swc.microservice.mission.drivers.digitaltwins

import com.azure.digitaltwins.core.BasicDigitalTwin
import com.azure.digitaltwins.core.BasicDigitalTwinMetadata
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import swc.microservice.mission.entities.Mission
import swc.microservice.mission.entities.MissionStep
import swc.microservice.mission.entities.OrdinaryWaste
import swc.microservice.mission.entities.TypeOfMission
import swc.microservice.mission.entities.TypeOfWaste

class MissionDigitalTwinManagerTest : FreeSpec({
    val manager = MissionDigitalTwinManager()

    val missionId = "MissionTest${System.currentTimeMillis()}"
    val collectionPointId = "CollectionPointTest${System.currentTimeMillis()}"

    val collectionPoint = BasicDigitalTwin(collectionPointId)
        .setMetadata(BasicDigitalTwinMetadata().setModelId("dtmi:swc:CollectionPoint;1"))
        .addToContents("Position", "{ \"Latitude\": 0, \"Longitude\": 0 }")
    val mission = Mission(
        missionId = missionId,
        truckId = null,
        typeOfWaste = TypeOfWaste(OrdinaryWaste.PAPER),
        typeOfMission = TypeOfMission.ORDINARY,
        missionSteps = listOf(MissionStep(collectionPointId))
    )

    "The mission manager" - {
        "when communicating with Azure Digital Twins" - {
            "should create a digital twin with its relationships" {
                manager.createDigitalTwin(collectionPoint) shouldBe collectionPointId
                manager.createMission(mission) shouldBe missionId
            }
            "should read digital twins" {
                shouldNotThrow<Exception> {
                    manager.getMission(missionId)
                }
            }
            "should delete digital twins" {
                manager.deleteMission(missionId) shouldBe mission
                shouldNotThrow<Exception> {
                    manager.deleteDigitalTwin(collectionPointId)
                }
            }
        }
    }
})
