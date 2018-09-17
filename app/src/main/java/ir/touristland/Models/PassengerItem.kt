package ir.touristland.Models

import java.io.Serializable

class PassengerItem : Serializable {
    var id = ""
    var fullName = ""
    var birthDate = ""
    var nationalCode = ""
    var sex = ""

    companion object {

        const val serialVersionUID = 1L
    }
}
