package com.phisit.weatherforecast.domain.usecase

import com.phisit.weatherforecast.data.response.Success
import com.phisit.weatherforecast.domain.model.GeocodingModel
import com.phisit.weatherforecast.domain.repository.WeatherServiceRepository
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class GetGeocodingUseCaseTest {

    private val weatherServiceRepository: WeatherServiceRepository = mockk()

    private lateinit var getGeocodingUseCase: GetGeocodingUseCase

    @BeforeEach
    fun setup() {
        getGeocodingUseCase = GetGeocodingUseCaseImpl(weatherServiceRepository)
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

    @Test
    fun `Get Geocoding with empty string return empty list`() = runTest {
        getGeocodingUseCase("")
            .collectLatest { result ->
                assertNotNull(result)
                assertTrue {
                    result is Success &&
                            result.data.isEmpty()
                }
            }
    }

    @Test
    fun `Get Geocoding with Bangkok when get Geocoding successful should return Geocoding list`() =
        runTest {
            val mockupGeocodingList = mockupGeocodingList()

            every {
                weatherServiceRepository.getGeocodingFromCity(any())
            }.returns(
                flowOf(Success(mockupGeocodingList))
            )

            getGeocodingUseCase("bangkok")
                .collectLatest { result ->
                    assertNotNull(result)
                    assertTrue {
                        result is Success &&
                                result.data.isNotEmpty()
                    }
                }
        }
}