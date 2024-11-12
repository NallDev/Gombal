package com.nalldev.gombal.splash.di

import com.nalldev.gombal.splash.SplashViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val splashModule = module {
    viewModel { SplashViewModel(get(), get()) }
}