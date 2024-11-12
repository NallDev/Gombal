package com.nalldev.core.domain.repositories

import kotlinx.coroutines.flow.Flow

interface DarkModeRepository {
    val isDarkMode: Flow<Boolean>
    suspend fun setIsDarkMode(isDarkMode: Boolean)
}