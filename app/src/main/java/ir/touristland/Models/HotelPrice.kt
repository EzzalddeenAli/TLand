package ir.touristland.Models

import java.io.Serializable

class HotelPrice : Serializable {
    var id = ""
    var nameFa = ""
    var fullBoard = ""
    var beds = ""
    var onlineReservation = ""
    var reservationStatus = ""
    var totalPrice: String? = null
    var priceList = ""
    var discount = ""

    companion object {

        const val serialVersionUID = 1L
    }
}
