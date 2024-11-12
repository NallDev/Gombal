package com.nalldev.core.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class PreferenceDataSource (
    private val dataStore: DataStore<Preferences>,
    private val ioDispatcher: CoroutineDispatcher
) {
    val isDarkMode: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[IS_DARK_MODE_KEY] ?: false
        }

    suspend fun setIsDarkMode(isDarkMode: Boolean) = withContext(ioDispatcher) {
        dataStore.edit { preferences ->
            preferences[IS_DARK_MODE_KEY] = isDarkMode
        }
    }

    val isOnBoardingFinished: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[IS_ON_BOARDING_FINISHED_KEY] ?: false
        }

    suspend fun setIsOnBoardingFinished(isOnBoardingFinished: Boolean) = withContext(ioDispatcher) {
        dataStore.edit { preferences ->
            preferences[IS_ON_BOARDING_FINISHED_KEY] = isOnBoardingFinished
        }
    }

    companion object {
        private val IS_DARK_MODE_KEY = booleanPreferencesKey("isDarkMode")
        private val IS_ON_BOARDING_FINISHED_KEY = booleanPreferencesKey("isOnBoardingFinished")
    }
}