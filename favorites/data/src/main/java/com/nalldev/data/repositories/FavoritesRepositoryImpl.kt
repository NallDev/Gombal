package com.nalldev.data.repositories

import com.nalldev.core.domain.model.JobModel
import com.nalldev.data.datasource.LocalDataSource
import com.nalldev.data.utils.JobMapper
import com.nalldev.domain.repositories.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoritesRepositoryImpl(
    private val localDataSource: LocalDataSource
) : FavoritesRepository {
    override fun getFavoriteJobs(): Flow<List<JobModel>> = localDataSource.getFavoriteJobs().map {
        it.map { entity ->
            JobMapper.toDomain(entity)
        }
    }

    override suspend fun deleteFromFavorite(job: JobModel) {
        localDataSource.deleteFromFavorite(job.id)
    }
}