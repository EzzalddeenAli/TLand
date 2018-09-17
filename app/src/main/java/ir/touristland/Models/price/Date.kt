package ir.touristland.Models.price

class Date {
    var timeStamp: Int = 0
    var persianDate: Int = 0
    var englishDate: String? = null

    override fun toString(): String {
        return "Date{" +
                "timeStamp = '" + timeStamp + '\''.toString() +
                ",persianDate = '" + persianDate + '\''.toString() +
                ",englishDate = '" + englishDate + '\''.toString() +
                "}"
    }
}
