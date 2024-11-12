package com.nalldev.onboarding.di

import com.nalldev.onboarding.OnBoardingViewModel
import org.koin.dsl.module

val onBoardingModule = module {
    single { OnBoardingViewModel(get()) }
}