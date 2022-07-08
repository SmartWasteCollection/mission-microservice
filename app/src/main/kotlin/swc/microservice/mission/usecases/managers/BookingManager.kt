package swc.microservice.mission.usecases.managers

import swc.microservice.mission.entities.Booking

interface BookingManager {
    fun getBookings(): List<Booking>

    fun updateBooking(booking: Booking)
}
