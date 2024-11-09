package com.nalldev.home.data.datasource

import androidx.paging.PagingSource
import com.nalldev.core.data.local.room.dao.JobFavoritesDao
import com.nalldev.core.data.local.room.dao.JobsDao
import com.nalldev.core.data.local.room.dao.RemoteKeysDao
import com.nalldev.core.data.local.room.model.JobEntity
import com.nalldev.core.data.local.room.model.JobFavoritesEntity
import com.nalldev.core.data.local.room.model.RemoteKeys
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class LocalDataSource(
    private val jobsDao: JobsDao,
    private val jobFavoritesDao: JobFavoritesDao,
    private val remoteKeysDao: RemoteKeysDao,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun insertJobs(jobs: List<JobEntity>) = withContext(ioDispatcher) {
        jobsDao.insertJobs(jobs)
    }

    fun getJobs() : PagingSource<Int, JobEntity> = jobsDao.getJobs()

    suspend fun deleteJobs() = withContext(ioDispatcher) {
        jobsDao.deleteJobs()
    }

    suspend fun insertRemoteKeys(remoteKeys: List<RemoteKeys>) = withContext(ioDispatcher) {
        remoteKeysDao.insertRemoteKeys(remoteKeys)
    }

    suspend fun getRemoteKeysId(id: String) : RemoteKeys? = withContext(ioDispatcher) {
        remoteKeysDao.getRemoteKeysId(id)
    }

    suspend fun deleteRemoteKeys() = withContext(ioDispatcher) {
        remoteKeysDao.deleteRemoteKeys()
    }

    fun getFavoriteJobs() : Flow<List<JobFavoritesEntity>> = jobFavoritesDao.getJobs()

    suspend fun insertToFavorite(job: JobFavoritesEntity) = withContext(ioDispatcher) {
        jobFavoritesDao.insertJob(job)
    }

    suspend fun deleteFromFavorite(id: String) = withContext(ioDispatcher) {
        jobFavoritesDao.deleteJob(id)
    }
}