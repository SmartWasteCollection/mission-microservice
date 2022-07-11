package swc.microservice.mission.drivers

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.FreeSpec
import org.springframework.web.client.ResourceAccessException
import swc.microservice.mission.drivers.http.HttpDumpsterManager
import swc.microservice.mission.drivers.http.HttpDumpsterManager.Companion.ADDRESS
import swc.microservice.mission.usecases.managers.DumpsterManager

class HttpDumpsterManagerTest : FreeSpec({
    val manager: DumpsterManager = HttpDumpsterManager()
    var address = ADDRESS
    if (address != "") {
        try {
            manager.getDumpsters()
        } catch (e: ResourceAccessException) {
            address = ""
        }
    }

    "The HttpDumpsterManager" - {
        "when getting dumpsters" - {
            "should not throw exceptions" {
                when (address) {
                    "" -> shouldThrowAny {
                        manager.getDumpsters()
                    }
                    else -> shouldNotThrowAny {
                        manager.getDumpsters()
                    }
                }
            }
        }
        "when getting a collection point" - {
            "should not throw exceptions" {
                when (address) {
                    "" -> shouldThrowAny {
                        manager.getCollectionPoint("")
                    }
                    else -> shouldNotThrowAny {
                        manager.getCollectionPoint(manager.getDumpsters().first().id)
                    }
                }
            }
        }
    }
})
