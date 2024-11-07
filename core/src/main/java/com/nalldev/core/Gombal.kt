package com.nalldev.core

import android.app.Application
import com.nalldev.core.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

open class Gombal : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.INFO)
            androidContext(this@Gombal)
            modules(appModule)
        }
    }
}