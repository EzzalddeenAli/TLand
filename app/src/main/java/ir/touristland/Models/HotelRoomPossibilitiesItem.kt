package ir.touristland.Models

import java.io.Serializable

class HotelRoomPossibilitiesItem : Serializable {
    var id = ""
    var hotelRoomId = ""
    var centerId = ""
    var roomPossibilitiesId = ""
    var roomPossibilitiesName = ""
    var iconName = ""

    companion object {

        const val serialVersionUID = 1L
    }

}
