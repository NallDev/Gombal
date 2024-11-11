package com.nalldev.home.data.datasource

import com.nalldev.home.data.model.DataItem
import com.nalldev.home.data.model.JobApiResponse
import com.nalldev.home.data.network.ApiServices
import io.ktor.client.call.body
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class NetworkDataSource(
    private val apiServices: ApiServices,
    private val ioDispatcher: CoroutineDispatcher
) {
    fun fetchJobs(): Flow<List<DataItem>> = flow {
        emit(apiServices.fetchJobs().body<JobApiResponse>().data)
    }.flowOn(ioDispatcher)
}