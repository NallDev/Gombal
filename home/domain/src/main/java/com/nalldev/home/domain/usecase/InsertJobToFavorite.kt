package com.nalldev.home.domain.usecase

import com.nalldev.core.domain.model.JobModel
import com.nalldev.home.domain.repositories.JobRepository

class InsertJobToFavorite(
    private val repository: JobRepository
) {
    suspend operator fun invoke(job : JobModel) = repository.insertToFavorite(job)
}