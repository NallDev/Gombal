package com.nalldev.home.domain.repositories

import com.nalldev.core.utils.UIState
import com.nalldev.home.domain.model.JobModel
import kotlinx.coroutines.flow.Flow

interface JobRepository {
    fun getJobs(): Flow<UIState<List<JobModel>>>
}