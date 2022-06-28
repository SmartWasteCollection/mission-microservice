package swc.microservice.mission.usecases

import swc.microservice.mission.entities.*
import java.util.*
import kotlin.reflect.KSuspendFunction1

fun computeOrdinaryMission(dumpsterId: String): Mission = TODO()

suspend fun computeExtraordinaryMission(
    typeOfWaste: TypeOfWaste,
    bookingsRetriever: () -> KSuspendFunction1<TypeOfWaste, List<Booking>> = ::defaultBookingsRetriever
): Mission =
    Mission(
        date = Date(),
        typeOfWaste = typeOfWaste,
        typeOfMission = TypeOfMission.EXTRAORDINARY,
        missionSteps = bookingsRetriever()(typeOfWaste).map { MissionStep(it.position) }
    )

private fun defaultBookingsRetriever() = ::getBookings