package ir.touristland.Models

import java.util.HashMap

/**
 * Created by ZAMAN on 5/22/2018.
 */

class NumberPassenger private constructor() {
    var numberAdult = 1
    var numberChild = 0
    var numberBaby = 0
    var reqNo: Int = 0

    var params: Map<String, String> = HashMap()

    companion object {
        val instance = NumberPassenger()
    }
}
