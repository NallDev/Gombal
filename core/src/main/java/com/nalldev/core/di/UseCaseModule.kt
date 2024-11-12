package com.nalldev.core.di

import com.nalldev.core.domain.usecases.DarkModeUseCases
import com.nalldev.core.domain.usecases.IsDarkMode
import com.nalldev.core.domain.usecases.IsOnBoardingFinished
import com.nalldev.core.domain.usecases.OnBoardingUseCases
import com.nalldev.core.domain.usecases.SetDarkMode
import com.nalldev.core.domain.usecases.SetOnBoardingFinished
import org.koin.dsl.module

val useCaseModule = module {
    single { IsDarkMode(get()) }
    single { SetDarkMode(get()) }
    single { DarkModeUseCases(get(), get()) }

    single { SetOnBoardingFinished(get()) }
    single { IsOnBoardingFinished(get()) }
    single { OnBoardingUseCases(get(), get()) }
}