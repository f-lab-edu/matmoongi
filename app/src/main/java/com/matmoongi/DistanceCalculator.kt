package com.matmoongi

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.sin
import kotlin.math.sqrt

object DistanceCalculator {
    fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): String {
        val earthRadius = 6371 // 지구의 반지름 (단위: km)

        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)

        val a = sin(dLat / 2) * sin(dLat / 2) +
            cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
            sin(dLon / 2) * sin(dLon / 2)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        val distance = earthRadius * c

//        1.x km
        if (distance >= 1.0) {
            return String.format("%.1f", distance) + "km"
        }
//        100m, 200m, .., 900m
        if (distance >= 0.1) {
            return (floor(distance * 10) * 100).toInt().toString() + "m"
        }
//        10m, 20m, .., 90m
        return (floor(distance * 100) * 10).toInt().toString() + "m"
    }
}
