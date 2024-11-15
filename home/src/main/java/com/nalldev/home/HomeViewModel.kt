package com.nalldev.home

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nalldev.core.domain.callbacks.BiometricCallback
import com.nalldev.core.domain.model.BiometricConfig
import com.nalldev.core.domain.model.JobModel
import com.nalldev.core.domain.usecases.biometric.BiometricUseCases
import com.nalldev.core.domain.usecases.dark_mode.DarkModeUseCases
import com.nalldev.core.utils.BiometricStatus
import com.nalldev.core.utils.RemoteHelper
import com.nalldev.core.utils.SingleLiveEvent
import com.nalldev.core.utils.UIState
import com.nalldev.home.domain.usecase.JobUseCases
import io.ktor.client.plugins.ClientRequestException
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.concurrent.Executor

class HomeViewModel(
    private val application: Application,
    private val jobUseCases: JobUseCases,
    private val darkModeUseCases: DarkModeUseCases,
    private val biometricUseCases: BiometricUseCases
) : ViewModel() {
    val isDarkMode = darkModeUseCases.isDarkMode().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = false
    )

    private val _uiState = MutableStateFlow<UIState<List<JobModel>>>(UIState.Loading)
    val uiState: StateFlow<UIState<List<JobModel>>> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")

    private val _toastEvent = SingleLiveEvent<String>()
    val toastEvent: LiveData<String> = _toastEvent

    private val _biometricStatus = SingleLiveEvent<BiometricStatus>()
    val biometricStatus: LiveData<BiometricStatus> = _biometricStatus

    val isBiometricAvailable = biometricUseCases.checkBiometricAvailability()

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
                _uiState.update { UIState.Success(filteredJobs) }
            }
            .catch { throwable ->
                val message = when (throwable) {
                    is IOException -> RemoteHelper.noInternetMessage(application)
                    is ClientRequestException -> RemoteHelper.remoteErrorMessage(
                        application,
                        throwable.response.status.value
                    )

                    else -> RemoteHelper.errorDefaultMessage(application)
                }
                _uiState.update { UIState.Error }
                _toastEvent.postValue(message)
            }
            .launchIn(viewModelScope)
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.update { query }
    }

    fun updateFavoriteStatus(job: JobModel, isFavorite: Boolean) = viewModelScope.launch {
        if (isFavorite) {
            jobUseCases.insertJobToFavorite(job)
        } else {
            jobUseCases.deleteJobFromFavorite(job)
        }
    }

    fun setDarkMode(isDarkMode: Boolean) = viewModelScope.launch {
        darkModeUseCases.setDarkMode(isDarkMode)
    }

    fun authenticate(activity: AppCompatActivity, executor: Executor, config: BiometricConfig) {
        val promptInfo = biometricUseCases.createPromptInfo(config)
        val biometricPrompt = biometricUseCases.createBiometricPrompt(activity, executor, object :
            BiometricCallback {
            override fun onAuthenticationError(errMsg: CharSequence) {
                _biometricStatus.postValue(BiometricStatus.Error(errMsg.toString()))
            }

            override fun onAuthenticationFailed() {
                _biometricStatus.postValue(BiometricStatus.Failed)
            }

            override fun onAuthenticationSucceeded() {
                _biometricStatus.postValue(BiometricStatus.Success)
            }
        })

        biometricPrompt.authenticate(promptInfo)
    }
}