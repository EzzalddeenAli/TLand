package ir.touristland.Models

import java.util.HashMap

/**
 * Created by ZAMAN on 5/22/2018.
 */

class HotelDetail private constructor() {
    var StartDate = 1
    var EndDate = 0
    var Destination = 0

    var params: Map<String, String> = HashMap()

    companion object {
        val instance = HotelDetail()
    }
}
