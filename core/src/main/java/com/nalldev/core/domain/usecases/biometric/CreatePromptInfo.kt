package com.nalldev.core.domain.usecases.biometric

import androidx.biometric.BiometricPrompt
import com.nalldev.core.domain.model.BiometricConfig

class CreatePromptInfo {
    operator fun invoke(config: BiometricConfig) : BiometricPrompt.PromptInfo {
        return BiometricPrompt.PromptInfo.Builder()
            .setTitle(config.title)
            .setSubtitle(config.subtitle)
            .setDescription(config.description)
            .setNegativeButtonText(config.negativeButtonText)
            .build()
    }
}