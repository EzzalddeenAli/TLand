package ir.touristland.Models

import java.io.Serializable

class PlaceFeedItem : Serializable {
    var id = ""
    var name = ""
    var latitude = ""
    var longitude = ""
    var imageUrl = ""
    var distance = ""
    var visitApi = ""

    companion object {

        private const val serialVersionUID = 1L
    }
}
