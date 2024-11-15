package com.nalldev.core.domain.model

class BiometricConfig private constructor(
    val title: String,
    val subtitle: String,
    val description: String,
    val negativeButtonText: String
) {
    class Builder {
        private var title: String = "Authentication Required"
        private var subtitle: String = ""
        private var description: String = ""
        private var negativeButtonText: String = "Cancel"

        fun setTitle(title: String) = apply { this.title = title }
        fun setSubtitle(subtitle: String) = apply { this.subtitle = subtitle }
        fun setDescription(description: String) = apply { this.description = description }
        fun setNegativeButtonText(text: String) = apply { this.negativeButtonText = text }
        fun build(): BiometricConfig = BiometricConfig(title, subtitle, description, negativeButtonText)
    }
}