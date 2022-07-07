package swc.microservice.mission.drivers.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import swc.microservice.mission.entities.Mission
import swc.microservice.mission.entities.OrdinaryWaste

@RestController
@RequestMapping("/missions")
class MissionController {

    @GetMapping("/")
    fun getAllMissions(): List<Mission<*>> = TODO()

    @PostMapping("/ordinary")
    fun generateOrdinaryMission(@RequestBody body: String): Mission<OrdinaryWaste> = TODO()

    @PutMapping("/{id}/step")
    fun missionStep(@PathVariable id: String): Mission<*> = TODO()
}
