package com.nalldev.onboarding

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.nalldev.onboarding.databinding.ActivityOnboardingBinding
import kotlinx.coroutines.launch

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

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.page.collect { page ->
                    when (page) {
                        1 -> {
                            binding.main.post {
                                binding.main.transitionToState(R.id.start)
                            }
                        }

                        2 -> {
                            binding.main.post {
                                binding.main.transitionToState(R.id.end)
                            }
                        }

                        3 -> {
                            binding.main.post {
                                binding.main.transitionToState(R.id.three)
                            }
                        }

                        4 -> {
                            val uri = Uri.parse("gombal://home")
                            val intent = Intent(Intent.ACTION_VIEW, uri)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        }
                    }
                }
            }
        }

        binding.main.setTransitionListener(object : MotionLayout.TransitionListener{
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {
                viewModel.setFinishTransition(false)
            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {}

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
               viewModel.setFinishTransition(true)
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {}

        })

        binding.btnNext.setOnClickListener {
            viewModel.nextPage()
        }
    }
}