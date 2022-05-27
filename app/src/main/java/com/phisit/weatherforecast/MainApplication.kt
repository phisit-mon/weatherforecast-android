package com.phisit.weatherforecast

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // use modules
            androidContext(applicationContext)
            androidLogger()
            //modules()
        }

    }
}