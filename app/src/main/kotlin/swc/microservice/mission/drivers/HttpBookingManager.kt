package swc.microservice.mission.drivers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
import org.springframework.web.client.getForEntity
import swc.microservice.mission.entities.Booking
import swc.microservice.mission.entities.BookingStatus
import swc.microservice.mission.entities.ExtraordinaryWaste
import swc.microservice.mission.entities.TypeOfWaste
import swc.microservice.mission.usecases.managers.BookingManager
import java.time.Instant
import java.time.ZoneId
import java.util.concurrent.CompletableFuture

@Service
open class HttpBookingManager : BookingManager {
    companion object {
        private val dotenv: Dotenv = dotenv {
            ignoreIfMissing = true
        }
        private const val BOOKING_ADDRESS = "BOOKING_ADDRESS"
        val ADDRESS: String = dotenv[BOOKING_ADDRESS] ?: ""
    }

    private val restTemplate = RestTemplate()

    override fun getBookings(): List<Booking> = this.doGetBookings().get()

    override fun updateBooking(booking: Booking) {
        this.doUpdateBooking(booking)
    }

    @Async
    open fun doGetBookings(): CompletableFuture<List<Booking>> =
        CompletableFuture.completedFuture(
            restTemplate.getForEntity<String>(ADDRESS).body?.deserializeBookings() ?: listOf()
        )

    @Async
    open fun doUpdateBooking(booking: Booking) {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        CompletableFuture.completedFuture(restTemplate.exchange<Booking>(ADDRESS.plus(booking._id), HttpMethod.PUT, HttpEntity(booking, headers)).body)
    }
}

data class DeserializableBooking(
    val _id: String,
    val userId: String,
    val typeOfWaste: TypeOfWaste<ExtraordinaryWaste>,
    val datetime: String,
    val city: String,
    val province: String,
    val address: String,
    val status: BookingStatus = BookingStatus.PENDING
)

fun String.deserializeBookings(): List<Booking> =
    jacksonObjectMapper().readValue<List<DeserializableBooking>>(this).map {
        Booking(
            it._id,
            it.userId,
            it.typeOfWaste,
            Instant.ofEpochMilli(it.datetime.toLong()).atZone(ZoneId.systemDefault()).toLocalDate(),
            it.city,
            it.province,
            it.address,
            it.status
        )
    }
