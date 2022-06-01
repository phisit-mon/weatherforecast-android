package com.phisit.weatherforecast.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jraska.livedata.test
import com.phisit.weatherforecast.common.core.Constants
import com.phisit.weatherforecast.common.core.coroutine.TestCoroutineDispatcherProvider
import com.phisit.weatherforecast.core.unittest.TaskExecutorExtension
import com.phisit.weatherforecast.core.unittest.TestCoroutinesExtension
import com.phisit.weatherforecast.data.response.Success
import com.phisit.weatherforecast.domain.model.CurrentModel
import com.phisit.weatherforecast.domain.model.GeocodingModel
import com.phisit.weatherforecast.domain.model.WeatherModel
import com.phisit.weatherforecast.domain.usecase.GetForecastWeatherUseCase
import com.phisit.weatherforecast.domain.usecase.GetGeocodingUseCase
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension

@ExtendWith(TaskExecutorExtension::class)
class HomeViewModelTest {
    @RegisterExtension
    @JvmField
    val dispatcher = TestCoroutinesExtension()

    private val getGeocodingUseCase: GetGeocodingUseCase = mockk()
    private val getForecastWeatherUseCase: GetForecastWeatherUseCase = mockk()
    private val coroutineDispatcherProvider = TestCoroutineDispatcherProvider(dispatcher.dispatcher)

    lateinit var viewModel: HomeViewModel


    @BeforeEach
    fun setup() {
        viewModel = HomeViewModel(
            getGeocodingUseCase, getForecastWeatherUseCase, coroutineDispatcherProvider
        )
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    private fun mockupGeocodingList(): List<GeocodingModel> {
        return listOf(
            GeocodingModel(
                country = "Bangkok",
                lat = 99.7,
                lon = 100.2
            ),
            GeocodingModel(
                country = "London",
                lat = 12.0,
                lon = 100.2
            )
        )
    }

    private fun mockupForecastWeather(): WeatherModel {
        return WeatherModel(
            current = CurrentModel(),
            lat = 88.0,
            lon = 59.9,
            timezoneOffset = 200,
            timezone = "Bangkok"
        )
    }

    @Test
    fun `Get Geocoding with city's name when get success result should return first value`() {
        val mockupGeo = mockupGeocodingList()

        every {
            getGeocodingUseCase(any())
        }.returns(
            flowOf(Success(mockupGeo))
        )

        viewModel.getGeocoding("London")

       val result = viewModel.getGeocodingLiveData()
            .test()
        result.assertHasValue()
        result.assertValue(mockupGeo.first())
    }

    @Test
    fun `Get ForecastWeather with lat, lon when success result should return weather currently`(){
        val mockupWeather = mockupForecastWeather()

        every {
            getForecastWeatherUseCase(any(), any(), any(), any())
        }.returns(
            flowOf(Success(mockupWeather))
        )

        viewModel.getForecastWeather(lat = 88.0, lon=55.0)

        val result = viewModel.getWeatherLiveData().test()
        result.assertHasValue()
        result.assertValue(mockupWeather.current)
    }

    @Test
    fun `Set unitOfTime with TEMP_UNIT_C when geocoding is null should do nothing`() {
        viewModel.setUnitsOfTemp(Constants.TEMP_UNIT_C)

        verify(exactly = 0) {
            getForecastWeatherUseCase(any(), any(), any(), any())
        }
    }

    @Test
    fun `Set unitOfTime with TEMP_UNIT_C when has geocoding already should call ForecastWeatherUseCase`() {
        val mockupGeo = mockupGeocodingList()
        val mockupWeather = mockupForecastWeather()

        every {
            getGeocodingUseCase(any())
        }.returns(
            flowOf(Success(mockupGeo))
        )

        every {
            getForecastWeatherUseCase(any(), any(), any(), any())
        }.returns(
            flowOf(Success(mockupWeather))
        )

        viewModel.getGeocoding()
        viewModel.setUnitsOfTemp(Constants.TEMP_UNIT_C)

        verify(exactly = 1) {
            getForecastWeatherUseCase(any(), any(), any(), any())
        }
    }
}