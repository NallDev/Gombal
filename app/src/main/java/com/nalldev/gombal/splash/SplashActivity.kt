package com.nalldev.gombal.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.nalldev.core.utils.CommonHelper
import com.nalldev.core.utils.RootCheckerHelper
import com.nalldev.core.utils.showShortToast
import com.nalldev.gombal.R
import com.nalldev.gombal.databinding.ActivitySplashBinding
import com.nalldev.gombal.splash.di.splashModule
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySplashBinding.inflate(layoutInflater) }

    private val viewModel by viewModel<SplashViewModel>()

    private val isDeviceRooted by lazy {
        (RootCheckerHelper.checkRootedFiles() || RootCheckerHelper.checkRootedProcesses() || RootCheckerHelper.checkTagsAndKeys())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        loadKoinModules(splashModule)

        initObserver()
    }

    private fun initObserver() = with(viewModel) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    isDarkMode.collect { isDarkMode ->
                        CommonHelper.setDarkMode(this@SplashActivity, isDarkMode)
                    }
                }

                launch {
                    isOnBoardingFinished.collect { isOnBoardingFinished ->
                        binding.main.post {
                            binding.main.transitionToEnd {
                                if (!isDeviceRooted) {
                                    if (isOnBoardingFinished) {
                                        goToHome()
                                    } else {
                                        goToOnBoarding()
                                    }
                                } else {
                                    showShortToast("Device rooted, you can't use this app")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun goToHome() {
        val uri = Uri.parse("gombal://home")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun goToOnBoarding() {
        val intent = Intent(this, Class.forName("com.nalldev.onboarding.OnboardingActivity"))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}