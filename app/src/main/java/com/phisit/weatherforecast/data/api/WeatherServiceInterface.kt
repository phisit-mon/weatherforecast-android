package com.phisit.weatherforecast.data.api

import androidx.annotation.WorkerThread
import com.phisit.weatherforecast.data.response.GeocodingResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherServiceInterface {
    @WorkerThread
    @GET("/geo/1.0/direct")
    suspend fun getDirectGeocoding(
        @Query("q") city: String
    ): GeocodingResponseModel

    @WorkerThread
    @GET("/data/2.5/onecall")
    suspend fun getWeatherForecast(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("exclude") exclude: String
    )
}