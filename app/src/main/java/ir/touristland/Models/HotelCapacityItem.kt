package ir.touristland.Models

import java.io.Serializable

class HotelCapacityItem : Serializable {
    var id = ""
    var hotelRoomId = ""
    var centerId = ""
    var capicityId = ""
    var cityCode = ""
    var price = ""
    var roomTypeId = ""
    var descriptRoom: String? = null
    var bedCount: String? = null
    var maxAdult = ""
    var maxChild = ""
    var roomCount = 0

    companion object {

        const val serialVersionUID = 1L
    }
}
