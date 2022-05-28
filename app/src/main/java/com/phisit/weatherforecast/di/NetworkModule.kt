package com.phisit.weatherforecast.di

import com.phisit.weatherforecast.BuildConfig
import com.phisit.weatherforecast.data.api.WeatherServiceInterface
import com.phisit.weatherforecast.network.NetworkProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val networkModule = module {
    single<WeatherServiceInterface> {
        NetworkProvider.build(
            context = androidContext(),
            url = BuildConfig.API_URL
        )
    }
}