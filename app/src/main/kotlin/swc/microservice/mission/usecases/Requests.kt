package swc.microservice.mission.usecases

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.headers
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import swc.microservice.mission.entities.Booking
import swc.microservice.mission.entities.BookingStatus
import swc.microservice.mission.entities.TypeOfWaste
import swc.microservice.mission.entities.Waste
import swc.microservice.mission.usecases.RequestBuilder.CLIENT
import swc.microservice.mission.usecases.RequestBuilder.buildRequest
import swc.microservice.mission.usecases.RequestBuilder.withBody

suspend fun <T : Waste> getPendingBookings(typeOfWaste: TypeOfWaste<T>): List<Booking<T>> =
    CLIENT.request(buildRequest(HttpMethod.Get, service = Service.BOOKING, route = "/bookings"))
        .body<List<Booking<T>>>()
        .filter { it.typeOfWaste == typeOfWaste }
        .filter { it.status == BookingStatus.PENDING }
        .take(MAX_EXTRAORDINARY_MISSION_STEPS)

suspend inline fun <reified T : Waste> updateBookings(bookings: List<Booking<T>>) {
    bookings.map {
        buildRequest(HttpMethod.Put, service = Service.BOOKING, route = "/bookings/${it.id}")
            .withBody(Booking(it.id, it.typeOfWaste, it.position, BookingStatus.REQUESTED))
    }.forEach { CLIENT.request(it) }
}

fun <T : Waste> deserialize(s: String): List<Booking<T>> = TODO()

object RequestBuilder {
    val CLIENT: HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                }
            )
        }
    }
    private const val BASE_URL: String = "http://swc/"
    private val SERVICES: Map<Service, String> = mapOf(
        Pair(Service.BOOKING, "booking")
    )

    fun buildRequest(
        httpMethod: HttpMethod,
        baseUrl: String = BASE_URL,
        service: Service,
        route: String
    ): HttpRequestBuilder {
        val builder = HttpRequestBuilder()
        builder.method = httpMethod
        builder.url {
            protocol = URLProtocol.HTTP
            host = baseUrl
            path(SERVICES[service] + route)
        }
        builder.headers {
            append(HttpHeaders.Accept, "application/json")
        }
        return builder
    }

    inline fun <reified T> HttpRequestBuilder.withBody(body: T): HttpRequestBuilder {
        this.contentType(ContentType.Application.Json)
        this.setBody(body)
        return this
    }
}

enum class Service {
    BOOKING
}
