package com.phisit.weatherforecast.data.repository

import com.phisit.weatherforecast.common.core.exception.NetworkError
import com.phisit.weatherforecast.common.core.exception.NetworkThrowable
import com.phisit.weatherforecast.data.api.WeatherServiceInterface
import com.phisit.weatherforecast.data.response.*
import com.phisit.weatherforecast.domain.repository.WeatherServiceRepository
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherServiceRepositoryTest {

    private val weatherServiceInterface: WeatherServiceInterface = mockk()

    private lateinit var weatherServiceRepository: WeatherServiceRepository

    @BeforeEach
    fun setup() {
        weatherServiceRepository = WeatherServiceRepositoryImpl(weatherServiceInterface)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    private fun mockupGeoList(): ArrayList<GeocodingResponseModel> {
        return arrayListOf(
            GeocodingResponseModel(
                country = "TH",
                lat = 76.5,
                lon = 40.3,
                name = "Bangkok"
            ),
            GeocodingResponseModel(
                country = "US",
                lat = 176.5,
                lon = 240.3,
                name = "London"
            )
        )
    }

    private fun mockupWeatherResponse(): WeatherResponseModel {
        return WeatherResponseModel(
            lat = 78.0,
            current = CurrentResponseModel(
                weather = listOf(
                    WeatherDetailResponseModel(),
                    WeatherDetailResponseModel()
                )
            ),
            lon = 99.9,
            timezone = "Bangkok/TH",
            timezoneOffset = 1000
        )
    }

    @Test
    fun `Get Geographic by city name when api success should return success with Geographic list`() =
        runTest {
            val listGeo = mockupGeoList()

            coEvery { weatherServiceInterface.getDirectGeocoding(any()) }
                .returns(Response.success(listGeo))

            val result = weatherServiceRepository.getGeocodingFromCity("Bangkok")
                .first()

            assertNotNull(result)
            assertTrue {
                result is Success && result.data.isNotEmpty()
            }
        }

    @Test
    fun `Get Geographic by city name when api error 404 should Failure with NetworkError_NotFound`() =
        runTest {
            coEvery { weatherServiceInterface.getDirectGeocoding(any()) }
                .returns(
                    Response.error(
                        404,
                        byteArrayOf().toResponseBody()
                    )
                )

            val result = weatherServiceRepository.getGeocodingFromCity("Bangkok")
                .first() as? Failure

            assertNotNull(result)
            assertEquals(
                NetworkError.NotFound,
                (result.exception as NetworkThrowable).error
            )
        }

    @Test
    fun `Get WeatherForecast when api return success and body isn't null should success with WeatherResponseModel`() =
        runTest {
            val mockupWeatherModel = mockupWeatherResponse()

            coEvery {
                weatherServiceInterface.getForecastWeather(
                    lat = any(),
                    lon = any(),
                    exclude = any()
                )
            }.returns(Response.success(mockupWeatherModel))

            val result = weatherServiceRepository.getForecastWeather(
                lat = 78.0,
                lon = 99.9,
                exclude = "current,minutely"
            )
                .first()

            assertNotNull(result)
            assertTrue {
                result is Success
            }

        }

    @Test
    fun `Get WeatherForecast when api return success but body's null should Failure with NetworkException_Failure`() =
        runTest {

            coEvery {
                weatherServiceInterface.getForecastWeather(
                    lat = any(),
                    lon = any(),
                    exclude = any()
                )
            }.returns(Response.success(null))

            val result = weatherServiceRepository.getForecastWeather(
                lat = 78.0,
                lon = 99.9,
                exclude = "current,minutely"
            ).first() as? Failure

            assertNotNull(result)
            assertTrue {
                result.exception is NetworkThrowable &&
                        (result.exception as NetworkThrowable).error == NetworkError.ResponseNull
            }
        }

    @Test
    fun `Get WeatherForecast when api return 500 should Failure with NetworkException_InternalServerError`() =
        runTest {

            coEvery {
                weatherServiceInterface.getForecastWeather(
                    lat = any(),
                    lon = any(),
                    exclude = any()
                )
            }.returns(Response.error(500, byteArrayOf().toResponseBody()))

            val result = weatherServiceRepository.getForecastWeather(
                lat = 78.0,
                lon = 99.9,
                exclude = "current,minutely"
            ).first() as? Failure

            assertNotNull(result)
            assertTrue {
                result.exception is NetworkThrowable &&
                        (result.exception as NetworkThrowable).error == NetworkError.InternalServerError
            }
        }
}