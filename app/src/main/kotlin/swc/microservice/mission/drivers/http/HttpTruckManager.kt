package swc.microservice.mission.drivers.http

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.scheduling.annotation.Async
import org.springframework.web.client.RestTemplate
import swc.microservice.mission.entities.Truck
import swc.microservice.mission.usecases.managers.TruckManager
import java.util.Collections
import java.util.concurrent.CompletableFuture

open class HttpTruckManager : TruckManager {
    companion object {
        private val dotenv: Dotenv = dotenv {
            ignoreIfMissing = true
        }
        private const val TRUCK_MICROSERVICE = "TRUCK_MICROSERVICE"
        val AVAILABLE_TRUCKS: String = dotenv[TRUCK_MICROSERVICE]?.toString()?.plus("/trucks/available/") ?: ""
        val ASSIGN_TRUCK: String = dotenv[TRUCK_MICROSERVICE]?.toString()?.plus("/trucks/availability/") ?: ""
        val UPDATE_TRUCK: String = dotenv[TRUCK_MICROSERVICE]?.toString()?.plus("/trucks/occupiedVolume/") ?: ""
    }

    private val restTemplate = RestTemplate()
    private val headers = HttpHeaders().also {
        it.accept = Collections.singletonList(MediaType.APPLICATION_JSON)
    }
    private val entity = HttpEntity("body", headers)

    override fun getAvailableTrucks(): List<Truck> = this.doGetAvailableTrucks().get()

    override fun assignTruck(truckId: String, assigned: Boolean): Unit = this.doAssignTruck(truckId, assigned).get()

    override fun occupiedVolume(value: Double, truckId: String): Unit = this.doOccupiedVolume(value, truckId).get()

    @Async
    open fun doGetAvailableTrucks(): CompletableFuture<List<Truck>> =
        CompletableFuture.completedFuture(
            restTemplate.exchange(
                AVAILABLE_TRUCKS,
                HttpMethod.GET,
                entity,
                String::class.java
            ).body?.deserializeTrucks()
        )

    @Async
    open fun doAssignTruck(truckId: String, assigned: Boolean): CompletableFuture<Unit> =
        CompletableFuture.completedFuture(
            restTemplate.put(
                ASSIGN_TRUCK,
                toAssignedTruckPayload(truckId, assigned)
            )
        )

    @Async
    open fun doOccupiedVolume(value: Double, truckId: String): CompletableFuture<Unit> =
        CompletableFuture.completedFuture(
            restTemplate.put(
                UPDATE_TRUCK,
                toOccupiedVolumePayload(truckId, value)
            )
        )
}

private fun String.deserializeTrucks(): List<Truck> = jacksonObjectMapper().readValue(this)

private fun toAssignedTruckPayload(truckId: String, inMission: Boolean = true): String = "{\"truckId\": \"$truckId\", \"inMission\": \"$inMission\"}"

private fun toOccupiedVolumePayload(truckId: String, occupiedVolume: Double): String = "{\"truckId\": \"$truckId\", \"occupiedVolume\": { \"value\": \"$occupiedVolume\" } }"
