@file:OptIn(ExperimentalCoroutinesApi::class)

package com.phisit.weatherforecast.domain.usecase

import com.phisit.weatherforecast.common.core.Constants
import com.phisit.weatherforecast.data.response.Success
import com.phisit.weatherforecast.domain.model.WeatherModel
import com.phisit.weatherforecast.domain.repository.WeatherServiceRepository
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetForecastWeatherTest {

    private val weatherServiceRepository: WeatherServiceRepository = mockk()
    private lateinit var getForecastWeatherUseCase: GetForecastWeatherUseCase

    @BeforeEach
    private fun setup() {
        getForecastWeatherUseCase = GetForecastWeatherUseCaseImpl(weatherServiceRepository)
    }

    @AfterEach
    private fun tearDown() {
        clearAllMocks()
    }

    private fun mockupWeatherModel(): WeatherModel {
        return WeatherModel(
            current = null,
            lat = 88.0,
            lon = 991.0,
            timezone = "Bangkok",
            timezoneOffset = 2000
        )
    }

    @Test
    fun `Get forecast weather when success should return WeatherModel`() = runTest {
        val mockupWeather = mockupWeatherModel()

        every {
            weatherServiceRepository.getForecastWeather(
                lat = any(),
                lon = any(),
                exclude = any(),
                unitsOfTemp = any()
            )
        }.returns(flowOf(Success(mockupWeather)))

        getForecastWeatherUseCase(
            lat = 88.0,
            lon = 991.0,
            typeOfWeather = Constants.CURRENT_WEATHER,
            unitsOfTemp = Constants.TEMP_UNIT_C
        )
    }

}