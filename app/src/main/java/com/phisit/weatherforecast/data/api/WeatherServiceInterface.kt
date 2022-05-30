package com.phisit.weatherforecast.data.api

import androidx.annotation.WorkerThread
import com.phisit.weatherforecast.data.response.GeocodingResponseModel
import com.phisit.weatherforecast.data.response.WeatherResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherServiceInterface {
    @WorkerThread
    @GET("/geo/1.0/direct")
    suspend fun getDirectGeocoding(
        @Query("q") city: String
    ): Response<ArrayList<GeocodingResponseModel>>

    @WorkerThread
    @GET("/data/2.5/onecall")
    suspend fun getForecastWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude: String
    ): Response<WeatherResponseModel>
}