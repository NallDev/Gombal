package com.nalldev.home.domain.repositories

import com.nalldev.core.domain.model.JobModel
import kotlinx.coroutines.flow.Flow

interface JobRepository {
    fun getJobs(): Flow<List<JobModel>>
    suspend fun insertToFavorite(job: JobModel)
    suspend fun deleteFromFavorite(job: JobModel)
}