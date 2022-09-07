package com.android.aop.part2.toycrimenotification.util

import kotlin.math.asin
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.math.sin
import kotlin.math.cos

object DistanceManager {

    private const val R = 6372.8 * 1000

    /**
     * 두 좌표의 거리를 계산한다.
     *
     * @param lat1 위도1
     * @param lon1 경도1
     * @param lat2 위도2
     * @param lon2 경도2
     * @return 두 좌표의 거리(m)
     */
    fun getDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Int {
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2).pow(2.0) + sin(dLon / 2).pow(2.0) * cos(Math.toRadians(lat1)) * cos(
            Math.toRadians(lat2)
        )
        val c = 2 * asin(sqrt(a))
        return (R * c).toInt()
    }

    fun toStringDistance(distance: Int): String {
        return when (distance) {
            in 0..1000 -> {
                "${distance}M"
            }

            else -> {
                "약 ${String.format("%.1f", (distance.toDouble()).div(1000))}KM"
            }
        }

    }
}