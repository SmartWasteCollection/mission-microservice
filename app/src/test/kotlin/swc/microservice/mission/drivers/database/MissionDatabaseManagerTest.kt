package swc.microservice.mission.drivers.database

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import swc.microservice.mission.entities.Mission
import swc.microservice.mission.entities.MissionStep
import swc.microservice.mission.entities.toTypeOfMission
import swc.microservice.mission.entities.toTypeOfWaste
import java.util.UUID

class MissionDatabaseManagerTest : FreeSpec({
    val databaseName = "swc-test-${System.currentTimeMillis()}"
    val manager = MissionDatabaseManager(databaseName)

    val missionId = "MissionTest-${UUID.randomUUID()}"
    val truckId = "TruckTest-${UUID.randomUUID()}"
    val firstBooking = "BookingTest-${UUID.randomUUID()}"
    val secondBooking = "BookingTest-${UUID.randomUUID()}"

    val mission = Mission(
        missionId = missionId,
        typeOfWaste = "PAPER".toTypeOfWaste(),
        typeOfMission = "EXTRAORDINARY".toTypeOfMission(),
        missionSteps = listOf(MissionStep(firstBooking), MissionStep(secondBooking))
    )

    "The Database Manger" - {
        "when creating a mission" - {
            "should add it to the database" {
                manager.createMission(mission) shouldBe missionId
            }
        }
        "when reading missions" - {
            "should get them from the database" {
                manager.getMissions().size shouldBe 1
            }
            "should retrieve them by their id" {
                manager.getMissionById(missionId) shouldBe mission
            }
        }
        "when updating missions" - {
            "should complete mission steps" {
                manager.completeMissionStep(missionId)?.isCompleted() shouldBe false
                manager.completeMissionStep(missionId)?.isCompleted() shouldBe true
            }
            "should assign a mission to a truck" {
                manager.getMissionById(missionId)?.truckId shouldBe null
                manager.assignTruckToMission(missionId, truckId)?.truckId shouldBe truckId
            }
        }
        "when deleting a mission" - {
            "should delete it from the database" {
                manager.deleteMission(missionId) shouldBe mission
                manager.getMissions() shouldBe listOf()

                manager.deleteDatabase(databaseName)
            }
        }
    }
})
