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
import swc.microservice.mission.entities.CollectionPoint
import swc.microservice.mission.entities.Dumpster
import swc.microservice.mission.usecases.managers.DumpsterManager
import java.util.Collections
import java.util.concurrent.CompletableFuture

open class HttpDumpsterManager : DumpsterManager {
    companion object {
        private val dotenv: Dotenv = dotenv {
            ignoreIfMissing = true
        }
        private const val DUMPSTER_ADDRESS = "DUMPSTER_ADDRESS"
        val ADDRESS: String = dotenv[DUMPSTER_ADDRESS] ?: ""
    }

    private val restTemplate = RestTemplate()
    private val headers = HttpHeaders().also {
        it.accept = Collections.singletonList(MediaType.APPLICATION_JSON)
    }
    private val entity = HttpEntity("body", headers)

    override fun getDumpsters(): List<Dumpster> = this.doGetDumpsters().get()

    override fun getCollectionPoint(dumpsterId: String): CollectionPoint = this.doGetCollectionPoint(dumpsterId).get()

    @Async
    open fun doGetDumpsters(): CompletableFuture<List<Dumpster>> =
        CompletableFuture.completedFuture(restTemplate.exchange(ADDRESS, HttpMethod.GET, entity, String::class.java).body?.deserializeDumpsters())

    @Async
    open fun doGetCollectionPoint(dumpsterId: String): CompletableFuture<CollectionPoint> =
        CompletableFuture.completedFuture(
            restTemplate.getForObject(
                ADDRESS.plus(dumpsterId).plus("/collectionpoint"),
                CollectionPoint::class.java
            )
        )
}

private fun String.deserializeDumpsters(): List<Dumpster> = jacksonObjectMapper().readValue(this)
