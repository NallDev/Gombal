package com.nalldev.favorites.di

import com.nalldev.favorites.FavoritesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val favoritesModule = module {
    viewModel { FavoritesViewModel(get()) }
}