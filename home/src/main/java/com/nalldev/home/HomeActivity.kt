package com.nalldev.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.core.util.Pair
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.nalldev.core.domain.model.BiometricConfig
import com.nalldev.home.data.di.dataModule
import com.nalldev.home.databinding.ActivityHomeBinding
import com.nalldev.home.databinding.ItemJobBinding
import com.nalldev.home.domain.di.domainModule
import com.nalldev.core.domain.model.JobModel
import com.nalldev.core.utils.BiometricStatus
import com.nalldev.core.utils.CommonHelper
import com.nalldev.core.utils.Constant
import com.nalldev.core.utils.UIState
import com.nalldev.core.utils.hideKeyboard
import com.nalldev.core.utils.showShortToast
import com.nalldev.home.di.homeModule
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class HomeActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModel<HomeViewModel>()

    private val jobAdapterListener by lazy {
        object : JobAdapter.Listener {
            override fun onItemClicked(job: JobModel, view: ItemJobBinding) {
                val uri = Uri.parse("gombal://detail")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                intent.putExtra(Constant.EXTRAS_DETAIL_KEY, job)

                view.tvTitle.transitionName = "title_${job.id}"
                view.tvCompanyName.transitionName = "company_name_${job.id}"
                view.tvLocation.transitionName = "location_${job.id}"

                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this@HomeActivity,
                    Pair(view.tvTitle, view.tvTitle.transitionName),
                    Pair(view.tvCompanyName, view.tvCompanyName.transitionName),
                    Pair(view.tvLocation, view.tvLocation.transitionName),
                )

                startActivity(intent, options.toBundle())
            }

            override fun onFavoriteClicked(job: JobModel, isFavorite: Boolean) {
                viewModel.updateFavoriteStatus(job, isFavorite)
            }
        }
    }

    private val jobAdapter by lazy {
        JobAdapter(jobAdapterListener)
    }

    private lateinit var biometricConfig: BiometricConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.swipe_refresh)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        loadKoinModules(listOf(homeModule, domainModule, dataModule))

        initView()
        initObserver()
        initListener()
    }

    private fun initView() = with(binding) {
        rvJobs.layoutManager = LinearLayoutManager(this@HomeActivity)
        rvJobs.adapter = jobAdapter

        biometricConfig = BiometricConfig.Builder()
            .setTitle("Gombal")
            .setSubtitle("Authentication Required")
            .setDescription("Please authenticate to continue")
            .setNegativeButtonText("Close")
            .build()
    }

    private fun initObserver() = with(viewModel) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    isDarkMode.collect { isDarkMode ->
                        if (viewModel.isDarkMode.value) {
                            binding.themeLayout.post {
                                binding.themeLayout.progress = 1f
                            }
                        } else {
                            binding.themeLayout.post {
                                binding.themeLayout.progress = 0f
                            }
                        }
                        CommonHelper.setDarkMode(this@HomeActivity, isDarkMode)
                    }
                }

                launch {
                    uiState.collect { uiState ->
                        when (uiState) {
                            is UIState.Loading -> binding.swipeRefresh.isRefreshing = true
                            is UIState.Success -> {
                                binding.swipeRefresh.isRefreshing = false
                                jobAdapter.submitList(uiState.data)
                                binding.tvEmpty.isVisible = uiState.data.isEmpty()
                            }
                            is UIState.Error -> {
                                binding.swipeRefresh.isRefreshing = false
                            }
                        }
                    }
                }
            }
        }

        toastEvent.observe(this@HomeActivity) { message ->
            showShortToast(message)
        }

        biometricStatus.observe(this@HomeActivity) { status ->
            when(status) {
                is BiometricStatus.Error -> {}
                is BiometricStatus.Failed -> {}
                is BiometricStatus.Success -> navigateToFavorite()
            }
        }
    }

    private fun initListener() = with(binding) {
        swipeRefresh.setOnRefreshListener {
            viewModel.fetchJobs()
        }
        etSearchJobs.doOnTextChanged { text, _, _, _ ->
            viewModel.updateSearchQuery(text.toString())
        }
        btnFavorite.setOnClickListener {
            if (viewModel.isBiometricAvailable) {
                viewModel.authenticate(this@HomeActivity, ContextCompat.getMainExecutor(this@HomeActivity), biometricConfig)
            } else {
                navigateToFavorite()
            }
        }
        binding.toggleTheme.setOnClickListener {
            if (viewModel.isDarkMode.value) {
                binding.themeLayout.post {
                    binding.themeLayout.transitionToStart {
                        viewModel.setDarkMode(false)
                    }
                }
            } else {
                binding.themeLayout.post {
                    binding.themeLayout.transitionToEnd {
                        viewModel.setDarkMode(true)
                    }
                }
            }
        }
    }

    private fun navigateToFavorite() {
        val intent = Intent(this@HomeActivity, Class.forName("com.nalldev.favorites.FavoritesActivity"))
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        hideKeyboard()
    }
}