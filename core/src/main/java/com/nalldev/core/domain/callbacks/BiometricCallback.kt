package com.nalldev.core.domain.callbacks

interface BiometricCallback {
    fun onAuthenticationError(errMsg: CharSequence)
    fun onAuthenticationFailed()
    fun onAuthenticationSucceeded()
}