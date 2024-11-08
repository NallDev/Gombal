package com.nalldev.home.domain.usecase

import com.nalldev.home.domain.repositories.JobRepository

class GetJobs(
    private val repository: JobRepository
) {
    operator fun invoke() = repository.getJobs()
}