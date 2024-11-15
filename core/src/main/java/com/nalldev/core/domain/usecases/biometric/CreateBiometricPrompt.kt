package com.nalldev.core.domain.usecases.biometric

import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import com.nalldev.core.domain.callbacks.BiometricCallback
import java.util.concurrent.Executor

class CreateBiometricPrompt {
    operator fun invoke(activity: FragmentActivity, executor: Executor, callback: BiometricCallback) : BiometricPrompt {
        return BiometricPrompt(activity, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                callback.onAuthenticationError(errString)
            }

            override fun onAuthenticationFailed() {
                callback.onAuthenticationFailed()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                callback.onAuthenticationSucceeded()
            }
        })
    }
}