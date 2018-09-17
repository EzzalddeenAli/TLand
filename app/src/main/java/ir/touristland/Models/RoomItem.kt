package ir.touristland.Models

import java.io.Serializable

class RoomItem : Serializable {

    var roomId = ""
    var roomTypeName = ""
    var roomNumber = ""
    var price = ""
    var bedCount = ""
    var maxAdult = ""
    var maxChild = ""
    var roomImgIdsStr = ""
    var roomCount = 0
    lateinit var roomAttributes: List<String>

    companion object {

        const val serialVersionUID = 1L
    }
}
