package com.nalldev.home.domain.repositories

import androidx.paging.PagingData
import com.nalldev.home.domain.model.JobModel
import kotlinx.coroutines.flow.Flow

interface JobRepository {
    fun getJobs(): Flow<PagingData<JobModel>>
    suspend fun insertToFavorite(job: JobModel)
    suspend fun deleteFromFavorite(job: JobModel)
}