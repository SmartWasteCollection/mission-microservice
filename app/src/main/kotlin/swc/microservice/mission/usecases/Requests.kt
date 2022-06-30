package swc.microservice.mission.usecases

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import swc.microservice.mission.entities.Booking
import swc.microservice.mission.entities.TypeOfWaste
import swc.microservice.mission.entities.Waste
import swc.microservice.mission.usecases.RequestBuilder.CLIENT
import swc.microservice.mission.usecases.RequestBuilder.buildRequest

suspend fun <T : Waste> getBookings(typeOfWaste: TypeOfWaste<T>): List<Booking<T>> =
    CLIENT.request(buildRequest(HttpMethod.Get, service = Service.BOOKING, route = "/pendingBookings"))
        .body<List<Booking<T>>>()
        .filter { it.typeOfWaste == typeOfWaste }


fun <T : Waste> deserialize(s: String): List<Booking<T>> = TODO()

object RequestBuilder {
    val CLIENT: HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
    }
    private const val BASE_URL: String = "http://swc/"
    private val SERVICES: Map<Service, String> = mapOf(
        Pair(Service.BOOKING, "booking")
    )

    fun buildRequest(httpMethod: HttpMethod, baseUrl: String = BASE_URL, service: Service, route: String): HttpRequestBuilder {
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
}

enum class Service {
    BOOKING
}