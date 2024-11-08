package com.nalldev.home.data.network

import com.nalldev.core.data.network.END_POINT
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class ApiServices(private val client : HttpClient) {
    suspend fun getJobs() = client.get(END_POINT)
}