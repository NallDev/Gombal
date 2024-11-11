package com.nalldev.home.data.network

import com.nalldev.core.data.network.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class ApiServices(private val client : HttpClient) {
    suspend fun fetchJobs() = client.get(String.format("${BASE_URL}/job-board-api"))
}