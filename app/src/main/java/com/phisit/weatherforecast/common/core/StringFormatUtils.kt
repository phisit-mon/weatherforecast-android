package com.phisit.weatherforecast.common.core

object StringFormatUtils {

    fun Int.toPercentageString(): String {
        return String.format("%d%%", this)
    }

    fun Double.toKiloMeter(): String {
        return String.format("%.2fkm/h", this)
    }
}