package ir.touristland.Models

import java.io.Serializable
import java.util.ArrayList

class CenterItem : Serializable {

    var id = 0
    var name = ""
    var createDate = ""
    var telCenter = ""
    var address = ""
    var modirName = ""
    var modirTel = ""
    var email = ""
    var fax = ""
    var imgIds = ""
    var descript = ""
    var listAttributs: List<String> = ArrayList()

    companion object {

        const val serialVersionUID = 1L
    }

}
