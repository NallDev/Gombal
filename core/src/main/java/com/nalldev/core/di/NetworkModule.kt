package com.nalldev.core.di

import com.nalldev.core.data.network.provideHttpClient
import com.nalldev.core.data.network.provideOkHttpClient
import org.koin.dsl.module

val networkModule = module {
    single { provideOkHttpClient() }
    single { provideHttpClient(get()) }
}