package com.nalldev.core.di

import com.nalldev.core.data.local.room.provideDatabase
import com.nalldev.core.data.local.room.provideSupportFactory
import org.koin.dsl.module

val databaseModule = module {
    single { provideSupportFactory() }
    single { provideDatabase(get(), get()) }
}

