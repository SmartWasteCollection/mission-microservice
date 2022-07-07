package swc.microservice.mission.usecases.managers

import swc.microservice.mission.entities.Booking
import swc.microservice.mission.entities.Waste

interface BookingManager {
    fun getBookings(): List<Booking<Waste>>

    fun updateBooking(bookingId: String): Booking<Waste>
}
