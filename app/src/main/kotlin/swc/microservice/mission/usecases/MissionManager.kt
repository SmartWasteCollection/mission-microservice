package swc.microservice.mission.usecases

import swc.microservice.mission.entities.Booking
import swc.microservice.mission.entities.ExtraordinaryWaste
import swc.microservice.mission.entities.Mission
import swc.microservice.mission.entities.MissionStep
import swc.microservice.mission.entities.OrdinaryWaste
import swc.microservice.mission.entities.TypeOfMission
import swc.microservice.mission.entities.TypeOfWaste
import java.time.LocalDate

const val MAX_EXTRAORDINARY_MISSION_STEPS: Int = 5

fun computeOrdinaryMission(dumpsterId: String): Mission<OrdinaryWaste> = TODO()

suspend fun computeExtraordinaryMission(
    typeOfWaste: TypeOfWaste<ExtraordinaryWaste>,
    bookingsRetriever: suspend (TypeOfWaste<ExtraordinaryWaste>) -> List<Booking<ExtraordinaryWaste>> =
        { getPendingBookings(typeOfWaste) },
    bookingsUpdater: suspend (List<Booking<ExtraordinaryWaste>>) -> Unit =
        { updateBookings(bookingsRetriever(typeOfWaste)) }
): Mission<ExtraordinaryWaste> =
    Mission(
        date = LocalDate.now(),
        typeOfWaste = typeOfWaste,
        typeOfMission = TypeOfMission.EXTRAORDINARY,
        missionSteps = bookingsRetriever(typeOfWaste)
            .also { bookingsUpdater(it) }
            .map { MissionStep(it.position) }
    )
