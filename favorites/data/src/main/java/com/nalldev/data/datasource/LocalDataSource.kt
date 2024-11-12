package com.nalldev.data.datasource

import com.nalldev.core.data.local.room.dao.JobFavoritesDao
import com.nalldev.core.data.local.room.model.JobFavoritesEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class LocalDataSource(
    private val jobFavoritesDao: JobFavoritesDao,
    private val ioDispatcher: CoroutineDispatcher
) {
    fun getFavoriteJobs() : Flow<List<JobFavoritesEntity>> = jobFavoritesDao.getJobs()

    suspend fun deleteFromFavorite(id: String) = withContext(ioDispatcher) {
        jobFavoritesDao.deleteJob(id)
    }
}