package com.nalldev.data.di

import com.nalldev.data.datasource.LocalDataSource
import com.nalldev.data.repositories.FavoritesRepositoryImpl
import com.nalldev.domain.repositories.FavoritesRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val favoritesDataModule = module {
    single { LocalDataSource(get(), get(named("IODispatcher"))) }
    single<FavoritesRepository> {
        FavoritesRepositoryImpl(
            localDataSource = get()
        )
    }
}