package com.nalldev.home.data.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.nalldev.core.data.local.room.JobDb
import com.nalldev.core.data.local.room.model.JobEntity
import com.nalldev.core.data.local.room.model.RemoteKeys
import com.nalldev.home.data.util.JobMapper

@OptIn(ExperimentalPagingApi::class)
class JobRemoteMediator(
    private val networkDataSource: NetworkDataSource,
    private val jobDb: JobDb
) : RemoteMediator<Int, JobEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, JobEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val responseData = networkDataSource.fetchJobs(page)

            val endOfPaginationReached = responseData.isEmpty()

            jobDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    jobDb.remoteKeysDao().deleteRemoteKeys()
                    jobDb.jobsDao().deleteJobs()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = responseData.map {
                    RemoteKeys(id = it.slug, prevKey = prevKey, nextKey = nextKey)
                }
                val jobEntity = responseData.map {
                    JobMapper.networkToDb(it)
                }
                jobDb.remoteKeysDao().insertRemoteKeys(keys)
                jobDb.jobsDao().insertJobs(jobEntity)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, JobEntity>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { story ->
            jobDb.remoteKeysDao().getRemoteKeysId(story.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, JobEntity>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { story ->
            jobDb.remoteKeysDao().getRemoteKeysId(story.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, JobEntity>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                jobDb.remoteKeysDao().getRemoteKeysId(id)
            }
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
        const val INVALID_PAGE = -1
    }
}