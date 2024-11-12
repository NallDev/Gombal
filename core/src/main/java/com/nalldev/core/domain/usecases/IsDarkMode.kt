package com.nalldev.core.domain.usecases

import com.nalldev.core.domain.repositories.DarkModeRepository

class IsDarkMode(
    private val darkModeRepository: DarkModeRepository
) {
    operator fun invoke() = darkModeRepository.isDarkMode
}