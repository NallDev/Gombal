package com.nalldev.home.data.repositories

import com.nalldev.home.data.datasource.LocalDataSource
import com.nalldev.home.data.util.JobMapper
import com.nalldev.core.domain.model.JobModel
import com.nalldev.home.data.datasource.NetworkDataSource
import com.nalldev.home.domain.repositories.JobRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class JobRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val networkDataSource: NetworkDataSource
) : JobRepository {

    override fun getJobs(): Flow<List<JobModel>> =
        combine(
            networkDataSource.fetchJobs(),
            localDataSource.getFavoriteJobs()
        ) { networkJobs, favoriteJobs ->
            networkJobs.map { jobEntity ->
                val isFavorite = favoriteJobs.any { it.id == jobEntity.slug }
                JobMapper.toDomain(jobEntity).copy(isFavorite = isFavorite)
            }
        }

    override suspend fun insertToFavorite(job: JobModel) {
        localDataSource.insertToFavorite(JobMapper.toJobFavoritesData(job))
    }

    override suspend fun deleteFromFavorite(job: JobModel) {
        localDataSource.deleteFromFavorite(JobMapper.toJobFavoritesData(job).id)
    }
}