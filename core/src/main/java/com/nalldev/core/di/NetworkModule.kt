package com.nalldev.core.di

import com.nalldev.core.data.network.provideCertificatePinner
import com.nalldev.core.data.network.provideHttpClient
import com.nalldev.core.data.network.provideLoggingInterceptor
import com.nalldev.core.data.network.provideOkHttpClient
import org.koin.dsl.module

val networkModule = module {
    single { provideCertificatePinner() }
    single { provideLoggingInterceptor() }
    single { provideOkHttpClient(get(), get()) }
    single { provideHttpClient(get()) }
}