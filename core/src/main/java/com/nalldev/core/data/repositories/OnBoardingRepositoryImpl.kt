package com.nalldev.core.data.repositories

import com.nalldev.core.data.local.datastore.PreferenceDataSource
import com.nalldev.core.domain.repositories.OnBoardingRepository
import kotlinx.coroutines.flow.Flow

class OnBoardingRepositoryImpl(
    private val preferenceDataSource: PreferenceDataSource
) : OnBoardingRepository {
    override val isOnBoardingFinished: Flow<Boolean> = preferenceDataSource.isOnBoardingFinished

    override suspend fun setIsOnBoardingFinished(isOnBoardingFinished: Boolean) {
        preferenceDataSource.setIsOnBoardingFinished(isOnBoardingFinished)
    }
}