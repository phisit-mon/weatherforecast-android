package com.phisit.weatherforecast.di

import com.phisit.weatherforecast.BuildConfig
import com.phisit.weatherforecast.common.core.coroutine.CoroutineDispatcherProvider
import com.phisit.weatherforecast.common.core.coroutine.DefaultCoroutineDispatcherProvider
import com.phisit.weatherforecast.data.api.WeatherServiceInterface
import com.phisit.weatherforecast.data.repository.WeatherServiceRepositoryImpl
import com.phisit.weatherforecast.domain.repository.WeatherServiceRepository
import com.phisit.weatherforecast.domain.usecase.GetForecastWeatherUseCase
import com.phisit.weatherforecast.domain.usecase.GetForecastWeatherUseCaseImpl
import com.phisit.weatherforecast.domain.usecase.GetGeocodingUseCase
import com.phisit.weatherforecast.domain.usecase.GetGeocodingUseCaseImpl
import com.phisit.weatherforecast.network.NetworkProvider
import com.phisit.weatherforecast.presentation.home.HomeViewModel
import com.phisit.weatherforecast.presentation.search.SearchViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val networkModule = module {
    single<WeatherServiceInterface> {
        NetworkProvider.build(
            context = androidContext(),
            url = BuildConfig.API_URL
        )
    }
}

val weatherModule = module {

    single<CoroutineDispatcherProvider> {
        DefaultCoroutineDispatcherProvider()
    }

    factory<WeatherServiceRepository> {
        WeatherServiceRepositoryImpl(
            weatherServiceInterface = get()
        )
    }

    factory<GetForecastWeatherUseCase> {
        GetForecastWeatherUseCaseImpl(
            weatherServiceRepository = get()
        )
    }

    factory<GetGeocodingUseCase> {
        GetGeocodingUseCaseImpl(
            weatherServiceRepository = get()
        )
    }

    viewModel {
        HomeViewModel(
            getGeocodingUseCase = get(),
            getForecastWeatherUseCase = get(),
            coroutineDispatcherProvider = get()
        )
    }

    viewModel {
        SearchViewModel(
            getGeocodingUseCase = get()
        )
    }
}