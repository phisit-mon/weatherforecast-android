package com.phisit.weatherforecast.domain.repository

import com.phisit.weatherforecast.data.response.ResultResponse
import com.phisit.weatherforecast.domain.model.GeocodingModel
import com.phisit.weatherforecast.domain.model.WeatherModel
import kotlinx.coroutines.flow.Flow

interface WeatherServiceRepository {
    fun getGeocodingFromCity(city: String): Flow<ResultResponse<List<GeocodingModel>>>
    fun getForecastWeather(
        lat: Double,
        lon: Double,
        exclude: String,
        unitsOfTemp: String
    ): Flow<ResultResponse<WeatherModel>>
}
