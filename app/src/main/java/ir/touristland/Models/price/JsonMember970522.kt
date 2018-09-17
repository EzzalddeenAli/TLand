package ir.touristland.Models.price

class JsonMember970522 {
    var date: Date? = null
    var boardPrice: Double = 0.toDouble()
    var webservicePrice: Double = 0.toDouble()
    var extraPersonPrice: Double = 0.toDouble()
    var salesPrice: Double = 0.toDouble()
    var isReserveStatus: Boolean = false

    override fun toString(): String {
        return "JsonMember970522{" +
                "date = '" + date + '\''.toString() +
                ",boardPrice = '" + boardPrice + '\''.toString() +
                ",webservicePrice = '" + webservicePrice + '\''.toString() +
                ",extraPersonPrice = '" + extraPersonPrice + '\''.toString() +
                ",salesPrice = '" + salesPrice + '\''.toString() +
                ",reserveStatus = '" + isReserveStatus + '\''.toString() +
                "}"
    }
}
