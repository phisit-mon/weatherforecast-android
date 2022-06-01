package com.phisit.weatherforecast.domain.usecase

import com.phisit.weatherforecast.data.response.ResultResponse
import com.phisit.weatherforecast.data.response.Success
import com.phisit.weatherforecast.domain.model.GeocodingModel
import com.phisit.weatherforecast.domain.repository.WeatherServiceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface GetGeocodingUseCase {
    operator fun invoke(city: String): Flow<ResultResponse<List<GeocodingModel>>>
}

class GetGeocodingUseCaseImpl(
    private val weatherServiceRepository: WeatherServiceRepository
) : GetGeocodingUseCase {

    override fun invoke(city: String): Flow<ResultResponse<List<GeocodingModel>>> {
        return if (city.isNotEmpty()) {
            weatherServiceRepository.getGeocodingFromCity(city)
        } else {
            flowOf(Success(listOf()))
        }
    }

}