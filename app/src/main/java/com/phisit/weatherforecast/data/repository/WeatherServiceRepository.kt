package com.phisit.weatherforecast.data.repository

import com.phisit.weatherforecast.common.core.exception.NetworkError
import com.phisit.weatherforecast.common.core.exception.NetworkThrowable
import com.phisit.weatherforecast.common.core.exception.errorType
import com.phisit.weatherforecast.data.api.WeatherServiceInterface
import com.phisit.weatherforecast.data.response.*
import com.phisit.weatherforecast.domain.model.*
import com.phisit.weatherforecast.domain.repository.WeatherServiceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response


class WeatherServiceRepositoryImpl(
    private val weatherServiceInterface: WeatherServiceInterface
) : WeatherServiceRepository {

    override fun getGeocodingFromCity(city: String): Flow<ResultResponse<List<GeocodingModel>>> {
        return flow {
            weatherServiceInterface.getDirectGeocoding(city).let {
                val result = if (it.isSuccessful) {
                    Success(it.body().orEmpty())
                } else {
                    Failure(handleNetworkThrowable(response = it))
                }
                transformToGeocodingModel(result)
            }.run {
                emit(this)
            }
        }
    }

    override fun getForecastWeather(
        lat: Double,
        lon: Double,
        exclude: String,
        unitsOfTemp: String
    ): Flow<ResultResponse<WeatherModel>> {
        return flow {
            weatherServiceInterface.getForecastWeather(lat, lon, exclude, unitsOfTemp).let {
                val body = it.body()
                val result = if (it.isSuccessful && body != null) {
                    Success(body)
                } else if (it.isSuccessful && body == null) {
                    Failure(NetworkThrowable(NetworkError.ResponseNull))
                } else {
                    Failure(handleNetworkThrowable(response = it))
                }
                transformToWeatherModel(result)
            }.run {
                emit(this)
            }
        }
    }

    private fun handleNetworkThrowable(response: Response<*>): NetworkThrowable {
        return NetworkThrowable(
            error = response.code().errorType(),
            message = response.message()
        )
    }

    private fun transformToGeocodingModel(
        response: ResultResponse<List<GeocodingResponseModel>>
    ): ResultResponse<List<GeocodingModel>> {
        return when (response) {
            is Success -> response.data.map {
                GeocodingModel(
                    country = it.country,
                    lat = it.lat,
                    lon = it.lon,
                    name = it.name,
                    state = it.state
                )
            }.let {
                Success(it)
            }
            is Failure -> response
        }
    }

    private fun transformToWeatherModel(
        response: ResultResponse<WeatherResponseModel>
    ): ResultResponse<WeatherModel> {
        return when (response) {
            is Success -> {
                response.data.let { data ->
                    WeatherModel(
                        current = transformToDomainCurrentModel(data.current),
                        lat = data.lat,
                        lon = data.lon,
                        timezone = data.timezone,
                        timezoneOffset = data.timezoneOffset,
                        daily = transformToDomainDailyModel(data.daily)
                    ).run {
                        Success(this)
                    }
                }
            }
            is Failure -> response
        }
    }

    private fun transformToDomainCurrentModel(current: CurrentResponseModel?): CurrentModel? {
        return current?.let { it ->
            CurrentModel(
                clouds = it.clouds,
                dewPoint = it.dewPoint.toInt(),
                dt = it.dt,
                feelsLike = it.feelsLike,
                humidity = it.humidity,
                pressure = it.pressure,
                sunrise = it.sunrise,
                sunset = it.sunset,
                temp = it.temp.toInt(),
                uvi = it.uvi,
                visibility = it.visibility,
                weather = it.weather.map(::transformToWeatherDetailResponseModel),
                windDeg = it.windDeg,
                windGust = it.windGust,
                windSpeed = it.windSpeed
            )
        }
    }

    private fun transformToWeatherDetailResponseModel(
        weatherDetail: WeatherDetailResponseModel
    ): WeatherDetailModel {
        return weatherDetail.let {
            WeatherDetailModel(
                description = it.description,
                icon = it.icon,
                id = it.id,
                main = it.main
            )
        }
    }

    private fun transformToDomainDailyModel(dailyList: List<DailyResponseModel>): DailyModel? {
        return dailyList.firstOrNull()?.let {
            DailyModel(
                dewPoint = it.dewPoint,
                humidity = it.humidity,
                windSpeed = it.windSpeed,
                feelsLike = FeelsLikeModel(
                    day = it.feelsLike.day,
                    eve = it.feelsLike.eve,
                    morn = it.feelsLike.morn,
                    night = it.feelsLike.night
                )
            )
        }
    }
}