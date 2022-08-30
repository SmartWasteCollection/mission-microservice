package swc.microservice.mission.drivers.api

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import swc.microservice.mission.drivers.ManagerSupplierImpl
import swc.microservice.mission.entities.Mission
import swc.microservice.mission.entities.Waste
import swc.microservice.mission.entities.toExtraordinaryTypeOfWaste
import swc.microservice.mission.usecases.AssignTruckToMission
import swc.microservice.mission.usecases.CompleteMissionStep
import swc.microservice.mission.usecases.ComputeExtraordinaryMission
import swc.microservice.mission.usecases.ComputeOrdinaryMission
import swc.microservice.mission.usecases.GetAllMissions
import swc.microservice.mission.usecases.managers.ManagerSupplier

@RestController
@CrossOrigin
@RequestMapping("/missions")
class MissionController(private val managerSupplier: ManagerSupplier = ManagerSupplierImpl()) {

    @GetMapping
    fun getAllMissions(): List<Mission<Waste>> = GetAllMissions().execute(managerSupplier)

    @PostMapping("/ordinary")
    fun generateOrdinaryMission(@RequestBody dumpsterId: String): String =
        ComputeOrdinaryMission(dumpsterId).execute(managerSupplier)

    @PostMapping("/extraordinary")
    fun generateExtraordinaryMission(@RequestBody typeOfWaste: String): String =
        ComputeExtraordinaryMission(typeOfWaste.toExtraordinaryTypeOfWaste()).execute(managerSupplier)

    @PutMapping("/{id}/step")
    fun missionStep(@PathVariable id: String): Mission<Waste>? =
        CompleteMissionStep(id).execute(managerSupplier)

    @PutMapping("/{missionId}/truck")
    fun assignTruck(@PathVariable missionId: String, @RequestBody truckId: String): Mission<Waste>? =
        AssignTruckToMission(missionId, truckId).execute(managerSupplier)
}
