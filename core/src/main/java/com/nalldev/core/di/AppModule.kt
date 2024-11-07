package com.nalldev.core.di

import org.koin.dsl.module

val appModule = module {
    includes(networkModule, databaseModule)
}