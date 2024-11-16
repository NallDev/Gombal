package com.nalldev.onboarding

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.nalldev.onboarding.databinding.ActivityOnboardingBinding
import com.nalldev.onboarding.di.onBoardingModule
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class OnboardingActivity : AppCompatActivity() {

    private var _binding : ActivityOnboardingBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<OnBoardingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        loadKoinModules(onBoardingModule)

        initObserver()
        initListener()
    }

    private fun initObserver() = with(viewModel) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                page.collect { page ->
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
                            viewModel.setOnBoardingFinished()

                            val uri = Uri.parse("gombal://home")
                            val intent = Intent(Intent.ACTION_VIEW, uri)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        }
                    }
                }
            }
        }
    }

    private fun initListener() = with(binding) {
        main.setTransitionListener(object : MotionLayout.TransitionListener{
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

        btnNext.setOnClickListener {
            viewModel.nextPage()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}