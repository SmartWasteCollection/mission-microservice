package swc.microservice.mission.usecases

import swc.microservice.mission.entities.*
import java.util.*

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
        date = Date(),
        typeOfWaste = typeOfWaste,
        typeOfMission = TypeOfMission.EXTRAORDINARY,
        missionSteps = bookingsRetriever(typeOfWaste)
            .also { bookingsUpdater(it) }
            .map { MissionStep(it.position) }
    )
