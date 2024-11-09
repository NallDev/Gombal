package com.nalldev.home.data.datasource

import com.nalldev.home.data.model.DataItem
import com.nalldev.home.data.model.JobApiResponse
import com.nalldev.home.data.network.ApiServices
import io.ktor.client.call.body
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class NetworkDataSource(
    private val apiServices: ApiServices,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun fetchJobs(page : Int): List<DataItem> = withContext(ioDispatcher) {
        apiServices.fetchJobs(page).body<JobApiResponse>().data
    }
}