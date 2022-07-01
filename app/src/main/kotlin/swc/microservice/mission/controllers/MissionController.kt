package swc.microservice.mission.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import swc.microservice.mission.entities.ExtraordinaryWaste
import swc.microservice.mission.entities.Mission
import swc.microservice.mission.entities.toTypeOfWaste
import swc.microservice.mission.usecases.computeExtraordinaryMission

@RestController
class MissionController {
    @GetMapping("/extraordinary")
    suspend fun extraordinary(
        @RequestParam(value = "waste")
        waste: String
    ): Mission<ExtraordinaryWaste> =
        computeExtraordinaryMission(waste.toTypeOfWaste())
}
