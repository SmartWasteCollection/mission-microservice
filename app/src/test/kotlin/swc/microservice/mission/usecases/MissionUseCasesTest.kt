package swc.microservice.mission.usecases

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import swc.microservice.mission.entities.Booking
import swc.microservice.mission.entities.CollectionPoint
import swc.microservice.mission.entities.Dimension
import swc.microservice.mission.entities.Dumpster
import swc.microservice.mission.entities.ExtraordinaryWaste
import swc.microservice.mission.entities.Mission
import swc.microservice.mission.entities.MissionStep
import swc.microservice.mission.entities.OrdinaryWaste
import swc.microservice.mission.entities.Position
import swc.microservice.mission.entities.Size
import swc.microservice.mission.entities.TypeOfDumpster
import swc.microservice.mission.entities.TypeOfMission
import swc.microservice.mission.entities.TypeOfWaste
import swc.microservice.mission.entities.Volume
import swc.microservice.mission.entities.Waste
import swc.microservice.mission.entities.WasteColor
import swc.microservice.mission.usecases.managers.BookingManager
import swc.microservice.mission.usecases.managers.DumpsterManager
import swc.microservice.mission.usecases.managers.ManagerSupplier
import swc.microservice.mission.usecases.managers.MissionManager
import java.time.LocalDate
import java.util.UUID

class MissionUseCasesTest : FreeSpec({
    var missions: List<Mission<Waste>> = listOf()
    var bookings: List<Booking> = listOf(
        Booking("b1", "user1", TypeOfWaste(ExtraordinaryWaste.IRON, WasteColor.NONE), LocalDate.now(), "city1", "province1", "address1"),
        Booking("b2", "user2", TypeOfWaste(ExtraordinaryWaste.IRON, WasteColor.NONE), LocalDate.now(), "city2", "province2", "address2"),
        Booking(
            "b3",
            "user3",
            TypeOfWaste(ExtraordinaryWaste.TWIGS, WasteColor.NONE),
            LocalDate.now(),
            "city3",
            "province3",
            "address3"
        )
    )
    val dumpsters: List<Dumpster> = listOf(
        Dumpster(
            "d1",
            TypeOfDumpster(Size(Dimension.LARGE, 200.0), TypeOfWaste(OrdinaryWaste.PAPER, WasteColor.BLUE)),
            occupiedVolume = Volume(120.0)
        ),
        Dumpster(
            "d2",
            TypeOfDumpster(Size(Dimension.LARGE, 200.0), TypeOfWaste(OrdinaryWaste.PAPER, WasteColor.BLUE)),
            occupiedVolume = Volume(100.0)
        ),
        Dumpster(
            "d3",
            TypeOfDumpster(Size(Dimension.LARGE, 200.0), TypeOfWaste(OrdinaryWaste.PAPER, WasteColor.BLUE)),
            occupiedVolume = Volume(10.0)
        )
    )
    val collectionPoints: List<CollectionPoint> = listOf(
        CollectionPoint("cp1", Position(0, 0)),
        CollectionPoint("cp2", Position(0, 0)),
        CollectionPoint("cp3", Position(0, 0))
    )
    val id = "collectionPoint"
    val typeOfWaste = TypeOfWaste(ExtraordinaryWaste.ELECTRONICS, WasteColor.NONE)

    val missionManager: MissionManager = object : MissionManager {
        override fun completeMissionStep(missionId: String): Mission<Waste>? {
            missions = missions.map {
                when (it.missionId) {
                    missionId -> Mission(
                        it.missionId,
                        it.truckId,
                        it.date,
                        it.typeOfWaste,
                        it.typeOfMission,
                        listOf(MissionStep(id, true))
                    )
                    else -> it
                }
            }
            return missions.find { it.missionId == missionId }
        }

        override fun getMissions(): List<Mission<Waste>> = missions
        override fun getMissionById(missionId: String): Mission<Waste>? =
            missions.find { it.missionId == missionId }

        override fun assignTruckToMission(missionId: String, truckId: String): Mission<Waste>? {
            missions.find { it.missionId == missionId }?.truckId = truckId
            return missions.find { it.missionId == missionId }
        }

        override fun createMission(mission: Mission<Waste>): String {
            missions = missions + mission
            return mission.missionId
        }

        override fun createNewMissionId(): String = "Mission-${UUID.randomUUID()}"

        override fun deleteMission(missionId: String): Mission<Waste>? {
            val mission = getMissionById(missionId)
            missions = missions.filter { it.missionId != missionId }
            return mission
        }
    }

    val bookingManager: BookingManager = object : BookingManager {
        override fun getBookings(): List<Booking> = bookings

        override fun updateBooking(booking: Booking) {
            bookings = bookings.map {
                when (it._id) {
                    booking._id -> booking
                    else -> it
                }
            }
            bookings.find { it._id == booking._id }
        }
    }

    val dumpsterManager = object : DumpsterManager {
        override fun getDumpsters(): List<Dumpster> = dumpsters

        override fun getCollectionPoint(dumpsterId: String): CollectionPoint =
            collectionPoints[dumpsters.indexOfFirst { it.id == dumpsterId }]
    }

    val supplier: ManagerSupplier = object : ManagerSupplier {
        override fun mission(type: TypeOfMission): MissionManager = missionManager

        override fun mission(mission: Mission<Waste>): MissionManager = missionManager

        override fun dumpster(): DumpsterManager = dumpsterManager

        override fun booking(): BookingManager = bookingManager
    }

    "When performing the mission use cases" - {
        "The ComputeOrdinaryMission use case" - {
            "should create a mission" {
                val id = ComputeOrdinaryMission("d1").execute(supplier)
                missions.size shouldBe 1
                assert(missions.any { it.missionId == id })
                missions.find { it.missionId == id }?.typeOfMission shouldBe TypeOfMission.ORDINARY
            }
        }
        "The ComputeExtraordinaryMission use case" - {
            "should create a mission" {
                val id = ComputeExtraordinaryMission(typeOfWaste).execute(supplier)
                missions.size shouldBe 2
                assert(missions.any { it.missionId == id })
                missions.find { it.missionId == id }?.typeOfMission shouldBe TypeOfMission.EXTRAORDINARY
                missions.find { it.missionId == id }?.typeOfWaste shouldBe typeOfWaste
            }
        }
        "The GetAllMissions use case" - {
            "should get all missions" {
                GetAllMissions().execute(supplier) shouldBe missions
            }
        }
        "The CompleteMissionStep use case" - {
            "should complete a step of the mission" {
                val missionId = missions.first().missionId
                CompleteMissionStep(missionId).execute(
                    supplier
                )
                missions.size shouldBe 2
                missions.find { it.missionId == missionId }?.typeOfMission shouldBe missionManager.getMissionById(missionId)?.typeOfMission
                missions.find { it.missionId == missionId }?.typeOfWaste shouldBe missionManager.getMissionById(missionId)?.typeOfWaste
                assert(missions.find { it.missionId == missionId }?.missionSteps?.first()?.completed ?: false)
            }
        }
        "The AssignTruckToMission use case" - {
            "should add the truck id to the mission" {
                val missionId = missions.first().missionId
                val truckId = "myTruck"
                AssignTruckToMission(
                    missionId,
                    truckId
                ).execute(supplier)?.truckId shouldBe truckId
                missions.size shouldBe 2
                missions.find { it.missionId == missionId }?.typeOfMission shouldBe missionManager.getMissionById(missionId)?.typeOfMission
                missions.find { it.missionId == missionId }?.typeOfWaste shouldBe missionManager.getMissionById(missionId)?.typeOfWaste
                missions.find { it.missionId == missionId }?.truckId shouldBe truckId
            }
        }
    }
})
