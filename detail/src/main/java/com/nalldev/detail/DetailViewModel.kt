package com.nalldev.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nalldev.core.domain.model.JobModel
import com.nalldev.home.domain.usecase.JobUseCases
import kotlinx.coroutines.launch

class DetailViewModel(
    private val jobUseCases: JobUseCases
) : ViewModel() {
    fun updateFavoriteStatus(job: JobModel, isFavorite: Boolean) = viewModelScope.launch {
        if (isFavorite) {
            jobUseCases.insertJobToFavorite(job)
        } else {
            jobUseCases.deleteJobFromFavorite(job)
        }
    }
}