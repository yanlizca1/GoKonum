package com.emrah.gokonum.util

import kotlin.math.*

object DistanceCalculator {

    // İki nokta arası mesafe (km)
    fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val R = 6371.0 // Dünya yarıçapı km
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2) * sin(dLon / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return R * c
    }

    // Bir rota içindeki toplam mesafe
    fun calculateRouteDistance(locations: List<SavedLocation>): Double {
        if (locations.size < 2) return 0.0
        var total = 0.0
        for (i in 0 until locations.size - 1) {
            total += calculateDistance(
                locations[i].latitude, locations[i].longitude,
                locations[i + 1].latitude, locations[i + 1].longitude
            )
        }
        return total
    }
}
