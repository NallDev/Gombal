package com.nalldev.core.domain.usecases

import com.nalldev.core.domain.repositories.OnBoardingRepository

class IsOnBoardingFinished(
    private val onBoardingRepository: OnBoardingRepository
) {
    operator fun invoke() = onBoardingRepository.isOnBoardingFinished
}