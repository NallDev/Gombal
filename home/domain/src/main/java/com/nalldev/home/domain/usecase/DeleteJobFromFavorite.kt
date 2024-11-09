package com.nalldev.home.domain.usecase

import com.nalldev.home.domain.model.JobModel
import com.nalldev.home.domain.repositories.JobRepository

class DeleteJobFromFavorite(
    private val repository: JobRepository
) {
    suspend operator fun invoke(job : JobModel) = repository.deleteFromFavorite(job)
}