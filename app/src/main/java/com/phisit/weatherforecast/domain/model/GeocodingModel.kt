package com.phisit.weatherforecast.domain.model

data class GeocodingModel(
    val country: String = "",
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val name: String = "",
    val state: String = ""
)