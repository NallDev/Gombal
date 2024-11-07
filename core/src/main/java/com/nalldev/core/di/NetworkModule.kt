package com.nalldev.core.di

import com.nalldev.core.data.network.httpClientAndroid
import io.ktor.client.HttpClient
import org.koin.dsl.module

val networkModule = module {
    single { provideHttpClient() }
}

fun provideHttpClient(): HttpClient {
    return httpClientAndroid
}