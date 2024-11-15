package com.nalldev.gombal.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nalldev.core.domain.usecases.dark_mode.DarkModeUseCases
import com.nalldev.core.domain.usecases.onboarding.OnBoardingUseCases
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class SplashViewModel(
    darkModeUseCases: DarkModeUseCases,
    onBoardingUseCases: OnBoardingUseCases
) : ViewModel() {
    val isDarkMode = darkModeUseCases.isDarkMode().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = false
    )

    val isOnBoardingFinished = onBoardingUseCases.isOnBoardingFinished().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = false
    )
}