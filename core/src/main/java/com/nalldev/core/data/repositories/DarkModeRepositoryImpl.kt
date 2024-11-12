package com.nalldev.core.data.repositories

import com.nalldev.core.data.local.datastore.PreferenceDataSource
import com.nalldev.core.domain.repositories.DarkModeRepository
import kotlinx.coroutines.flow.Flow

class DarkModeRepositoryImpl(
    private val preferenceDataSource: PreferenceDataSource,
) : DarkModeRepository {
    override val isDarkMode: Flow<Boolean> = preferenceDataSource.isDarkMode

    override suspend fun setIsDarkMode(isDarkMode: Boolean) {
        preferenceDataSource.setIsDarkMode(isDarkMode)
    }
}