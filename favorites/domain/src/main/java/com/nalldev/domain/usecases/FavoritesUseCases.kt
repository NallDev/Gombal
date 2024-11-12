package com.nalldev.domain.usecases

data class FavoritesUseCases(
    val getFavoriteJobs: GetFavoriteJobs,
    val deleteJobFromFavorite: DeleteJobFromFavorite
)