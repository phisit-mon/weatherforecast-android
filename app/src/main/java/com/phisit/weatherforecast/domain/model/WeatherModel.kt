package com.phisit.weatherforecast.domain.model

data class WeatherModel(
    val current: CurrentModel? = null,
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val timezone: String = "",
    val timezoneOffset: Int = 0,
    val daily: DailyModel? = null
)

data class CurrentModel(
    val clouds: Int = 0,
    val dewPoint: Int = 0,
    val dt: Int = 0,
    val feelsLike: Double = 0.0,
    val humidity: Int = 0,
    val pressure: Int = 0,
    val temp: Int = 0,
    val weather: List<WeatherDetailModel> = listOf(),
    val windSpeed: Double = 0.0
)

data class WeatherDetailModel(
    val description: String = "",
    val icon: String = "",
    val id: Int = 0,
    val main: String = ""
)

data class DailyModel(
    val clouds: Int = 0,
    val dewPoint: Double = 0.0,
    val dt: Int = 0,
    val feelsLike: FeelsLikeModel = FeelsLikeModel(),
    val humidity: Int = 0,
    val weather: List<WeatherDetailModel> = listOf(),
    val windSpeed: Double = 0.0
)

data class FeelsLikeModel(
    val day: Int = 0,
    val eve: Int = 0,
    val morn: Int = 0,
    val night: Int = 0
)