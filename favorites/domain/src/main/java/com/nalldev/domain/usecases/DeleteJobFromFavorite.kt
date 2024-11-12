package com.nalldev.domain.usecases

import com.nalldev.core.domain.model.JobModel
import com.nalldev.domain.repositories.FavoritesRepository

class DeleteJobFromFavorite(
    private val favoritesRepository: FavoritesRepository
) {
    suspend operator fun invoke(job : JobModel) = favoritesRepository.deleteFromFavorite(job)
}