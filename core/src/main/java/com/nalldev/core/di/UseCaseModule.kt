package com.nalldev.core.di

import com.nalldev.core.domain.usecases.biometric.BiometricUseCases
import com.nalldev.core.domain.usecases.biometric.CheckBiometricAvailability
import com.nalldev.core.domain.usecases.biometric.CreateBiometricPrompt
import com.nalldev.core.domain.usecases.biometric.CreatePromptInfo
import com.nalldev.core.domain.usecases.dark_mode.DarkModeUseCases
import com.nalldev.core.domain.usecases.dark_mode.IsDarkMode
import com.nalldev.core.domain.usecases.onboarding.IsOnBoardingFinished
import com.nalldev.core.domain.usecases.onboarding.OnBoardingUseCases
import com.nalldev.core.domain.usecases.dark_mode.SetDarkMode
import com.nalldev.core.domain.usecases.onboarding.SetOnBoardingFinished
import org.koin.dsl.module

val useCaseModule = module {
    single { IsDarkMode(get()) }
    single { SetDarkMode(get()) }
    single { DarkModeUseCases(get(), get()) }

    single { SetOnBoardingFinished(get()) }
    single { IsOnBoardingFinished(get()) }
    single { OnBoardingUseCases(get(), get()) }

    single { CheckBiometricAvailability(get()) }
    single { CreatePromptInfo() }
    single { CreateBiometricPrompt() }
    single { BiometricUseCases(get(), get(), get()) }
}