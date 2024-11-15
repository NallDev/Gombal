package com.nalldev.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nalldev.core.domain.usecases.onboarding.OnBoardingUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OnBoardingViewModel(
    private val onBoardingUseCases: OnBoardingUseCases
) : ViewModel() {
    private val _page : MutableStateFlow<Int> = MutableStateFlow(1)
    val page : StateFlow<Int> = _page.asStateFlow()

    private var isTransitionFinish : Boolean = true

    fun nextPage() {
        if (!isTransitionFinish) return
        _page.value += 1
        isTransitionFinish = false
    }

    fun setFinishTransition(isFinished : Boolean) {
        isTransitionFinish = isFinished
    }

    fun setOnBoardingFinished() = viewModelScope.launch {
        onBoardingUseCases.setOnBoardingFinished(true)
    }
}