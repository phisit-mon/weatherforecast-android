package com.phisit.weatherforecast.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phisit.weatherforecast.data.response.Success
import com.phisit.weatherforecast.domain.model.GeocodingModel
import com.phisit.weatherforecast.domain.usecase.GetGeocodingUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

class SearchViewModel(
    private val getGeocodingUseCase: GetGeocodingUseCase
) : ViewModel() {

    private val geocodingLiveData: MutableLiveData<List<GeocodingModel>> = MutableLiveData()

    fun getGeocodingLiveData(): LiveData<List<GeocodingModel>> = geocodingLiveData

    fun searchGeocodingList(keyword: String) {
        getGeocodingUseCase(keyword)
            .catch { error ->
                Timber.e("error ${error.message}")
            }.onEach { result ->
                when (result) {
                    is Success -> {
                        Timber.e("getGeocodingList : ${result.data}")
                        geocodingLiveData.value = result.data
                    }
                    else -> Unit
                }
            }.launchIn(viewModelScope)
    }
}