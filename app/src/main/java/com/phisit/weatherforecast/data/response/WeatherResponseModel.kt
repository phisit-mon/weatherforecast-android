package com.phisit.weatherforecast.data.response
import com.google.gson.annotations.SerializedName


data class WeatherResponseModel(
    @SerializedName("current")
    val current: CurrentResponseModel? = null,
    @SerializedName("lat")
    val lat: Double = 0.0,
    @SerializedName("lon")
    val lon: Double = 0.0,
    @SerializedName("timezone")
    val timezone: String = "",
    @SerializedName("timezone_offset")
    val timezoneOffset: Int = 0,
    @SerializedName("daily")
    val daily: List<DailyResponseModel> = listOf()
)

data class CurrentResponseModel(
    @SerializedName("clouds")
    val clouds: Int = 0,
    @SerializedName("dew_point")
    val dewPoint: Double = 0.0,
    @SerializedName("dt")
    val dt: Int = 0,
    @SerializedName("feels_like")
    val feelsLike: Double = 0.0,
    @SerializedName("humidity")
    val humidity: Int = 0,
    @SerializedName("pressure")
    val pressure: Int = 0,
    @SerializedName("sunrise")
    val sunrise: Int = 0,
    @SerializedName("sunset")
    val sunset: Int = 0,
    @SerializedName("temp")
    val temp: Double = 0.0,
    @SerializedName("uvi")
    val uvi: Double = 0.0,
    @SerializedName("visibility")
    val visibility: Int = 0,
    @SerializedName("weather")
    val weather: List<WeatherDetailResponseModel> = listOf(),
    @SerializedName("wind_deg")
    val windDeg: Int = 0,
    @SerializedName("wind_gust")
    val windGust: Double = 0.0,
    @SerializedName("wind_speed")
    val windSpeed: Double = 0.0
)

data class WeatherDetailResponseModel(
    @SerializedName("description")
    val description: String = "",
    @SerializedName("icon")
    val icon: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("main")
    val main: String = ""
)

data class DailyResponseModel(
    @SerializedName("clouds")
    val clouds: Int = 0,
    @SerializedName("dew_point")
    val dewPoint: Double = 0.0,
    @SerializedName("dt")
    val dt: Int = 0,
    @SerializedName("feels_like")
    val feelsLike: FeelsLikeResponse = FeelsLikeResponse(),
    @SerializedName("humidity")
    val humidity: Int = 0,
    @SerializedName("moon_phase")
    val moonPhase: Double = 0.0,
    @SerializedName("moonrise")
    val moonrise: Int = 0,
    @SerializedName("moonset")
    val moonset: Int = 0,
    @SerializedName("pop")
    val pop: Double = 0.0,
    @SerializedName("pressure")
    val pressure: Int = 0,
    @SerializedName("rain")
    val rain: Double = 0.0,
    @SerializedName("sunrise")
    val sunrise: Int = 0,
    @SerializedName("sunset")
    val sunset: Int = 0,
    @SerializedName("uvi")
    val uvi: Double = 0.0,
    @SerializedName("weather")
    val weather: List<WeatherDetailResponseModel> = listOf(),
    @SerializedName("wind_deg")
    val windDeg: Int = 0,
    @SerializedName("wind_gust")
    val windGust: Double = 0.0,
    @SerializedName("wind_speed")
    val windSpeed: Double = 0.0
)

data class FeelsLikeResponse(
    @SerializedName("day")
    val day: Double = 0.0,
    @SerializedName("eve")
    val eve: Double = 0.0,
    @SerializedName("morn")
    val morn: Double = 0.0,
    @SerializedName("night")
    val night: Double = 0.0
)