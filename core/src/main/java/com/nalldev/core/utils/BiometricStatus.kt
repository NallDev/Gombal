package com.nalldev.core.utils

sealed interface BiometricStatus {
    data object Failed : BiometricStatus
    data object Success : BiometricStatus
    data class Error(val message: String) : BiometricStatus
}