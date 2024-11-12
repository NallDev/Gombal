package com.nalldev.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nalldev.core.domain.model.JobModel
import com.nalldev.domain.usecases.FavoritesUseCases
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val favoritesUseCases: FavoritesUseCases
) : ViewModel() {
    val favoriteJobs = favoritesUseCases.getFavoriteJobs().catch {

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )

    fun deleteFromFavorite(job: JobModel) = viewModelScope.launch {
        favoritesUseCases.deleteJobFromFavorite(job)
    }
}