package swc.microservice.mission.usecases.managers

import swc.microservice.mission.entities.Truck

interface TruckManager {
    fun getAvailableTrucks(): List<Truck>

    fun assignTruck(truckId: String, assigned: Boolean = true)

    fun occupiedVolume(value: Double = 0.0, truckId: String)
}
