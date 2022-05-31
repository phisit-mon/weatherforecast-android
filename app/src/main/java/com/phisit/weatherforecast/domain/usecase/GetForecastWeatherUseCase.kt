package com.phisit.weatherforecast.domain.usecase

import com.phisit.weatherforecast.data.response.ResultResponse
import com.phisit.weatherforecast.domain.model.WeatherModel
import com.phisit.weatherforecast.domain.repository.WeatherServiceRepository
import kotlinx.coroutines.flow.Flow

interface GetForecastWeatherUseCase {
    operator fun invoke(
        lat: Double,
        lon: Double,
        typeOfWeather: String,
        unitsOfTemp: String
    ): Flow<ResultResponse<WeatherModel>>
}

class GetForecastWeatherUseCaseImpl(
    private val weatherServiceRepository: WeatherServiceRepository
) : GetForecastWeatherUseCase {

    override fun invoke(
        lat: Double,
        lon: Double,
        typeOfWeather: String,
        unitsOfTemp: String
    ): Flow<ResultResponse<WeatherModel>> {
        return weatherServiceRepository.getForecastWeather(
            lat = lat,
            lon= lon,
            exclude = typeOfWeather,
            unitsOfTemp = unitsOfTemp
        )
    }

}