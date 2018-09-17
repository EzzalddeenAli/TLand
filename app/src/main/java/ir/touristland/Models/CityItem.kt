package ir.touristland.Models

import java.io.Serializable

class CityItem : Serializable {
    var code = ""
    var cityNameFa = ""

    override fun toString(): String {
        return cityNameFa
    }

    companion object {

        const val serialVersionUID = 1L
    }

}
