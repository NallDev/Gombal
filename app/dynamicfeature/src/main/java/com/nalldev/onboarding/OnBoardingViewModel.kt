package com.nalldev.onboarding

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class OnBoardingViewModel : ViewModel() {
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
}