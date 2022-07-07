package swc.microservice.mission.drivers

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.springframework.web.client.ResourceAccessException
import swc.microservice.mission.drivers.HttpBookingManager.Companion.ADDRESS
import swc.microservice.mission.entities.Booking
import swc.microservice.mission.entities.BookingStatus
import swc.microservice.mission.usecases.managers.BookingManager

class HttpBookingManagerTest : FreeSpec({
    val manager: BookingManager = HttpBookingManager()
    var address = ADDRESS
    if (address != "") {
        try {
            manager.getBookings()
        } catch (e: ResourceAccessException) {
            address = ""
        }
    }

    "The HttpBookingManager" - {
        "when getting bookings" - {
            "should not throw exceptions" {
                when (address) {
                    "" -> shouldThrowAny {
                        manager.getBookings()
                    }
                    else -> shouldNotThrowAny {
                        manager.getBookings()
                    }
                }
            }
        }
        "when updating a booking" - {
            "should update its status" {
                if (address != "") {
                    val bookings = manager.getBookings()
                    if (bookings.isNotEmpty()) {
                        val b = bookings.first()
                        manager.updateBooking(
                            Booking(
                                b._id,
                                b.userId,
                                b.typeOfWaste,
                                b.datetime,
                                b.city,
                                b.province,
                                b.address,
                                BookingStatus.ASSIGNED
                            )
                        )
                        manager.getBookings().find { it._id == b._id }?.status shouldBe BookingStatus.ASSIGNED
                    }
                }
            }
        }
    }
})
