package swc.microservice.mission.usecases

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import swc.microservice.mission.entities.Booking
import swc.microservice.mission.entities.BookingStatus
import swc.microservice.mission.entities.ExtraordinaryWaste
import swc.microservice.mission.entities.TypeOfWaste
import swc.microservice.mission.entities.Waste
import swc.microservice.mission.usecases.RequestBuilder.getBookingsRequest
import swc.microservice.mission.usecases.RequestBuilder.updateBookingsRequest

suspend inline fun <reified T : Waste> getPendingBookings(typeOfWaste: TypeOfWaste<T>): List<Booking<T>> =
    getBookingsRequest()
        .body<List<Booking<T>>>()
        .filter { it.typeOfWaste == typeOfWaste }
        .filter { it.status == BookingStatus.PENDING }
        .take(MAX_EXTRAORDINARY_MISSION_STEPS)

suspend inline fun updateBookings(bookings: List<Booking<ExtraordinaryWaste>>) {
    bookings.forEach {
        updateBookingsRequest(Booking(it._id, it.typeOfWaste, it.position, BookingStatus.REQUESTED))
    }
}

object RequestBuilder {
    private val CLIENT: HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }
    }
    private const val HTTP: String = "http://"
    private val SERVICES: Map<Service, String> = mapOf(
        Pair(Service.BOOKING, "booking"),
        Pair(Service.LOCAL, "localhost:3000"),
    )

    suspend fun getBookingsRequest(): HttpResponse =
        CLIENT.get("${HTTP}${SERVICES[Service.LOCAL]}/bookings")

    suspend fun updateBookingsRequest(body: Booking<ExtraordinaryWaste>): HttpResponse =
        CLIENT.put("${HTTP}${SERVICES[Service.LOCAL]}/bookings/${body._id}") {
            setBody(body)
            contentType(ContentType.Application.Json)
        }
}

enum class Service {
    BOOKING, LOCAL
}
