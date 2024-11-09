package com.nalldev.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.nalldev.core.utils.UIState
import com.nalldev.home.data.di.dataModule
import com.nalldev.home.databinding.ActivityHomeBinding
import com.nalldev.home.domain.di.domainModule
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class HomeActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModel<HomeViewModel>()

    private lateinit var jobAdapter: JobAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        loadKoinModules(listOf(homeModule, domainModule, dataModule))

        initView()
        initObserver()
    }

    private fun initView() = with(binding) {
        jobAdapter = JobAdapter()

        rvJobs.layoutManager = LinearLayoutManager(this@HomeActivity)
        rvJobs.adapter = jobAdapter
    }


    private fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.jobs.collect { pagingData ->
                    println("Paging data : $pagingData")
                    jobAdapter.submitData(pagingData)
                }
            }
        }
    }
}