package com.nalldev.core.utils

sealed interface UIState<out R> {
    data class Success<out T>(val data: T) : UIState<T>
    data class Error(val message: String) : UIState<Nothing>
    data object Loading : UIState<Nothing>
}