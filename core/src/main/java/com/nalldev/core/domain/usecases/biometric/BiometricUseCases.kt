package com.nalldev.core.domain.usecases.biometric

data class BiometricUseCases(
    val checkBiometricAvailability: CheckBiometricAvailability,
    val createPromptInfo: CreatePromptInfo,
    val createBiometricPrompt: CreateBiometricPrompt
)
