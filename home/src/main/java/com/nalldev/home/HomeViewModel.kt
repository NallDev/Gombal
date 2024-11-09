package com.nalldev.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.nalldev.home.domain.usecase.JobUseCases
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    jobUseCases: JobUseCases
) : ViewModel() {
    val jobs = jobUseCases.getJobs().stateIn(
        scope = viewModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
        initialValue = PagingData.empty()
    )
}