package com.phisit.weatherforecast.presentation.home

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.*
import com.phisit.weatherforecast.common.core.Constants
import com.phisit.weatherforecast.common.core.coroutine.CoroutineDispatcherProvider
import com.phisit.weatherforecast.data.response.Failure
import com.phisit.weatherforecast.data.response.Success
import com.phisit.weatherforecast.domain.model.CurrentModel
import com.phisit.weatherforecast.domain.model.DailyModel
import com.phisit.weatherforecast.domain.model.GeocodingModel
import com.phisit.weatherforecast.domain.usecase.GetForecastWeatherUseCase
import com.phisit.weatherforecast.domain.usecase.GetGeocodingUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeViewModel(
    private val getGeocodingUseCase: GetGeocodingUseCase,
    private val getForecastWeatherUseCase: GetForecastWeatherUseCase,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    companion object {
        private const val DEFAULT_CITY = "Bangkok"
    }

    private val geocodingLiveData: MutableLiveData<GeocodingModel> = MutableLiveData()
    private val weatherLiveData: MutableLiveData<CurrentModel> = MutableLiveData()
    private val dailyWeatherLiveData: MutableLiveData<DailyModel> = MutableLiveData()

    var unitsOfTemp = Constants.TEMP_UNIT_C
        private set

    private val currentCity: String
        get() = geocodingLiveData.value?.name ?: DEFAULT_CITY

    fun getGeocodingLiveData(): LiveData<GeocodingModel> = geocodingLiveData
    fun getWeatherLiveData(): LiveData<CurrentModel> = weatherLiveData
    fun getDailyWeatherLiveData(): LiveData<DailyModel> = dailyWeatherLiveData

    val forecastWeatherTrigger = geocodingLiveData.map { geoCoding ->
        getForecastWeather(geoCoding.lat, geoCoding.lon)
    }

    fun getGeocoding(city: String = currentCity) {
        getGeocodingUseCase(city)
            .flowOn(coroutineDispatcherProvider.io())
            .catch { error ->
                emit(Failure(error))
            }.onEach { result ->
                when (result) {
                    is Success -> {
                        updateGeocoding(result.data)
                    }
                    else -> Unit
                }
            }.launchIn(viewModelScope)
    }

    fun getForecastWeather(lat: Double, lon: Double, unitsOfTemp: String = this.unitsOfTemp) {
        getForecastWeatherUseCase(
            lat = lat,
            lon = lon,
            unitsOfTemp = unitsOfTemp,
            typeOfWeather = Constants.DAILY_WEATHER
        ).flowOn(coroutineDispatcherProvider.io())
            .catch { error ->
                emit(Failure(error))
            }.onEach { result ->
                when (result) {
                    is Success -> {
                        result.data.current?.let {
                            weatherLiveData.value = it
                        }
                        result.data.daily?.let {
                            dailyWeatherLiveData.value = it
                        }
                    }
                    is Failure -> Unit
                }
            }.launchIn(viewModelScope)
    }

    fun setUnitsOfTemp(unitsOfTemp: String) {
        this.unitsOfTemp = unitsOfTemp
        reloadForecastWeather()
    }

    private fun reloadForecastWeather() {
        val geoCoding = geocodingLiveData.value
        if (geoCoding != null) {
            getForecastWeather(geoCoding.lat, geoCoding.lon)
        }
    }

    private fun updateGeocoding(list: List<GeocodingModel>) {
        if (list.isNotEmpty()) {
            geocodingLiveData.value = list.first()
        }
    }

}
