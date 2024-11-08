package com.nalldev.gombal

import android.app.Application
import com.nalldev.core.di.databaseModule
import com.nalldev.core.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class Gombal : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.INFO)
            androidContext(this@Gombal)
            modules(listOf(networkModule, databaseModule))
        }
    }
}