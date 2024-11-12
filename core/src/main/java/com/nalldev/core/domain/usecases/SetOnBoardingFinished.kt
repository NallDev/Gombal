package com.nalldev.core.domain.usecases

import com.nalldev.core.domain.repositories.OnBoardingRepository


class SetOnBoardingFinished(
    private val onBoardingRepository: OnBoardingRepository
) {
    suspend operator fun invoke(onBoardingFinished: Boolean) = onBoardingRepository.setIsOnBoardingFinished(onBoardingFinished)
}