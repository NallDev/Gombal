package com.nalldev.onboarding

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.nalldev.onboarding.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityOnboardingBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<OnBoardingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel.page.observe(this) { page ->
            if (page == 2) {
                runOnUiThread {
                    binding.main.transitionToEnd()
                }
            } else if (page == 3) {
                runOnUiThread {
                    binding.main.transitionToState(R.id.three)
                }
            }
        }

        binding.btnNext.setOnClickListener {
            viewModel.nextPage()
        }
    }
}