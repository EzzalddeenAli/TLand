package ir.touristland.Models

import java.io.Serializable

class FilterFeedItem : Serializable {
    var id = ""
    var name = ""
    var hasChild = ""

    companion object {

        private const val serialVersionUID = 1L
    }
}
