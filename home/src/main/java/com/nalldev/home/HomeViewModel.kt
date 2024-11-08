package com.nalldev.home

import androidx.lifecycle.ViewModel
import com.nalldev.home.domain.usecase.JobUseCases

class HomeViewModel(
    jobUseCases: JobUseCases
) : ViewModel() {
    val getJobs = jobUseCases.getJobs()
}