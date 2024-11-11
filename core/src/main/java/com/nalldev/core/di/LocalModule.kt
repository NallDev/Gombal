package com.nalldev.core.di

import com.nalldev.core.data.local.datastore.provideDataStore
import com.nalldev.core.data.local.datastore.providePreferenceDatastore
import com.nalldev.core.data.local.room.provideDatabase
import com.nalldev.core.data.local.room.provideJobFavoritesDao
import com.nalldev.core.data.local.room.provideSupportFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module

val localModule = module {
    single { provideSupportFactory() }
    single { provideDatabase(get(), get()) }
    single { provideJobFavoritesDao(get()) }

    single { provideDataStore(get()) }
    single { providePreferenceDatastore(get(), get(named("IODispatcher"))) }
}

