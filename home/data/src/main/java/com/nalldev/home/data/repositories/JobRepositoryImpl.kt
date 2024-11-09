package com.nalldev.home.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.nalldev.home.data.datasource.JobRemoteMediator
import com.nalldev.home.data.datasource.LocalDataSource
import com.nalldev.home.data.util.JobMapper
import com.nalldev.home.domain.model.JobModel
import com.nalldev.home.domain.repositories.JobRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class JobRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val jobRemoteMediator: JobRemoteMediator
) : JobRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getJobs(): Flow<PagingData<JobModel>> =
        Pager(
            config = PagingConfig(
                pageSize = 100,
                initialLoadSize = 100,
            ),
            remoteMediator = jobRemoteMediator,
            pagingSourceFactory = { localDataSource.getJobs() }
        ).flow.combine(localDataSource.getFavoriteJobs()) { pagingData, favoriteJobs ->
            pagingData.map { jobEntity ->
                val isFavorite = favoriteJobs.any { it.id == jobEntity.id }
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