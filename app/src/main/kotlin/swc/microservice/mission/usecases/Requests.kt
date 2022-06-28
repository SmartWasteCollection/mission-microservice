package swc.microservice.mission.usecases

import swc.microservice.mission.entities.Booking
import swc.microservice.mission.entities.TypeOfWaste
import swc.microservice.mission.usecases.RequestBuilder.CLIENT
import swc.microservice.mission.usecases.RequestBuilder.buildRequest
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.concurrent.CompletableFuture

fun getBookings(typeOfWaste: TypeOfWaste): CompletableFuture<List<Booking>> =
    CLIENT.sendAsync(
        buildRequest(Method.GET, service = Service.BOOKING, route = "/pendingBookings"),
        HttpResponse.BodyHandlers.ofString()
    ).thenApply { r -> deserialize(r.body()) }

fun deserialize(s: String): List<Booking> = TODO()

object RequestBuilder {
    val CLIENT: HttpClient = HttpClient.newHttpClient()
    private const val BASE_URL: String = "http://swc/"
    private val SERVICES: Map<Service, String> = mapOf(
        Pair(Service.BOOKING, "booking")
    )
    private fun request(method: Method, url: String, payload: String? = null): HttpRequest =
        HttpRequest.newBuilder()
            .uri(URI.create(url))
            .method(method.toString(), HttpRequest.BodyPublishers.ofString(payload ?: ""))
            .build()

    fun buildRequest(method: Method, baseUrl: String = BASE_URL, service: Service, route: String): HttpRequest =
        request(method, baseUrl + SERVICES[service] + route)
}

enum class Service {
    BOOKING
}

enum class Method {
    GET, PUT, POST, DELETE

    override fun toString(): String = when (this) {
        GET -> "GET"
        PUT -> "PUT"
        POST -> "POST"
        DELETE -> "DELETE"
        else -> ""
    }
}