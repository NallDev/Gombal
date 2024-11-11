package com.nalldev.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nalldev.core.domain.model.JobModel
import com.nalldev.core.utils.RemoteHelper
import com.nalldev.core.utils.UIState
import com.nalldev.home.domain.usecase.JobUseCases
import io.ktor.client.plugins.ClientRequestException
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class HomeViewModel(
    private val application: Application,
    private val jobUseCases: JobUseCases
) : ViewModel() {
    private val _uiState = MutableStateFlow<UIState<List<JobModel>>>(UIState.Loading)
    val uiState: StateFlow<UIState<List<JobModel>>> = _uiState

    private val _searchQuery = MutableStateFlow("")

    init {
        fetchJobs()
    }

    @OptIn(FlowPreview::class)
    fun fetchJobs() {
        combine(
            jobUseCases.getJobs(),
            _searchQuery.debounce(300)
        ) { jobs, query ->
            if (query.isBlank()) {
                jobs
            } else {
                jobs.filter { job ->
                    job.title.contains(query, ignoreCase = true) ||
                            job.companyName.contains(query, ignoreCase = true) ||
                            job.location.contains(query, ignoreCase = true)
                }
            }
        }
            .onStart {
                _uiState.update { UIState.Loading }
            }
            .onEach { filteredJobs ->
                println("FAVORITE CUY : ${filteredJobs.map { it.isFavorite }.count()}")
                _uiState.update { UIState.Success(filteredJobs) }
            }
            .catch { throwable ->
                val message = when(throwable) {
                    is IOException -> RemoteHelper.noInternetMessage(application)
                    is ClientRequestException -> RemoteHelper.remoteErrorMessage(application, throwable.response.status.value)
                    else -> RemoteHelper.errorDefaultMessage(application)
                }
                _uiState.update { UIState.Error(message) }
            }
            .launchIn(viewModelScope)
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.update { query }
    }

    fun updateFavoriteStatus(job: JobModel, isFavorite: Boolean) {
        viewModelScope.launch {
            if (isFavorite) {
                jobUseCases.insertJobToFavorite(job)
            } else {
                jobUseCases.deleteJobFromFavorite(job)
            }
        }
    }
}