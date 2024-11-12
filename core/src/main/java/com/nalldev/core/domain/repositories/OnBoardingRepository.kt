package com.nalldev.core.domain.repositories

import kotlinx.coroutines.flow.Flow

interface OnBoardingRepository {
    val isOnBoardingFinished: Flow<Boolean>
    suspend fun setIsOnBoardingFinished(isOnBoardingFinished: Boolean)
}