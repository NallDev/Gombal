package com.nalldev.home.domain.usecase

data class JobUseCases(
    val getJobs: GetJobs,
    val insertJobToFavorite: InsertJobToFavorite,
    val deleteJobFromFavorite: DeleteJobFromFavorite
)
