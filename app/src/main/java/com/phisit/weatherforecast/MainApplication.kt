package com.phisit.weatherforecast

import android.app.Application
import com.phisit.weatherforecast.di.networkModule
import com.phisit.weatherforecast.di.weatherModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import timber.log.Timber

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            // use modules
            androidContext(applicationContext)
            androidLogger()
            modules(
                listOf(networkModule, weatherModule)
            )
        }

    }
}