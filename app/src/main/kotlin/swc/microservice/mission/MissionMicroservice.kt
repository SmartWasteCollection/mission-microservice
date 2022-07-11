package swc.microservice.mission

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class MissionMicroservice

fun main(args: Array<String>) {
    runApplication<MissionMicroservice>(*args)
}
