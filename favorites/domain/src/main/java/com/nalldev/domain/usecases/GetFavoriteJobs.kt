package com.nalldev.domain.usecases

import com.nalldev.domain.repositories.FavoritesRepository

class GetFavoriteJobs(
    private val favoritesRepository: FavoritesRepository
) {
    operator fun invoke() = favoritesRepository.getFavoriteJobs()
}