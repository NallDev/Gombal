package com.nalldev.onboarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nalldev.onboarding.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityOnboardingBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}