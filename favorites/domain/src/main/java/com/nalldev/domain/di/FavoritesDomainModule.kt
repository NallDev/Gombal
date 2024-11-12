package com.nalldev.domain.di

import com.nalldev.domain.usecases.DeleteJobFromFavorite
import com.nalldev.domain.usecases.FavoritesUseCases
import com.nalldev.domain.usecases.GetFavoriteJobs
import org.koin.dsl.module

val favoritesDomainModule = module {
    single { GetFavoriteJobs(get()) }
    single { DeleteJobFromFavorite(get()) }
    single { FavoritesUseCases(get(), get()) }
}