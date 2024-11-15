package com.nalldev.core.di

import androidx.biometric.BiometricManager
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

val commonModule = module {
    single(named("IODispatcher")) {
        Dispatchers.IO
    }

    single { BiometricManager.from(get()) }
}