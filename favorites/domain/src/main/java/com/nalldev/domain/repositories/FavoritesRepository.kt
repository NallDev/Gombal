package com.nalldev.domain.repositories

import com.nalldev.core.domain.model.JobModel
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun getFavoriteJobs(): Flow<List<JobModel>>

    suspend fun deleteFromFavorite(job: JobModel)
}