package com.nalldev.core.domain.usecases.dark_mode

import com.nalldev.core.domain.repositories.DarkModeRepository

class SetDarkMode(
    private val darkModeRepository: DarkModeRepository
) {
    suspend operator fun invoke(darkMode: Boolean) = darkModeRepository.setIsDarkMode(darkMode)
}