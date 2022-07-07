package swc.microservice.mission.usecases.managers

import swc.microservice.mission.entities.Booking

interface BookingManager {
    fun getBookings(): List<Booking>

    fun requestBooking(bookingId: String)

    fun fulfillBooking(bookingId: String)
}
