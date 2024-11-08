package com.nalldev.home.data.repositories

import com.nalldev.core.utils.UIState
import com.nalldev.home.data.model.JobApiResponse
import com.nalldev.home.data.network.ApiServices
import com.nalldev.home.data.util.JobMapper
import com.nalldev.home.domain.model.JobModel
import com.nalldev.home.domain.repositories.JobRepository
import io.ktor.client.call.body
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class JobRepositoryImpl(
    private val apiServices: ApiServices
) : JobRepository {
    override fun getJobs(): Flow<UIState<List<JobModel>>>  = flow {
        emit(UIState.Loading)
        try {
            val response = apiServices.getJobs().body<JobApiResponse>()
            val data = response.data.map {
                JobMapper.toDomain(it)
            }
            emit(UIState.Success(data))
        } catch (e: Exception) {
            emit(UIState.Error)
        }
    }.flowOn(Dispatchers.IO)
}