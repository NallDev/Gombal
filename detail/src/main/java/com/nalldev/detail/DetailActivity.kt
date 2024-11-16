package com.nalldev.detail

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.transition.AutoTransition
import android.view.Window
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.nalldev.core.domain.model.JobModel
import com.nalldev.core.utils.Constant
import com.nalldev.detail.databinding.ActivityDetailBinding
import com.nalldev.detail.di.detailModule
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class DetailActivity : AppCompatActivity() {

    private var _binding : ActivityDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupTransition()
        enableEdgeToEdge()
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        loadKoinModules(detailModule)

        getDetailExtras()?.let {
            initView(it)
            initListener(it)
        } ?: run {
            initListener(null)
        }
    }

    private fun getDetailExtras() : JobModel? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Constant.EXTRAS_DETAIL_KEY, JobModel::class.java)
        } else {
            intent.getParcelableExtra(Constant.EXTRAS_DETAIL_KEY)
        }
    }

    private fun initView(job : JobModel) = with(binding) {
        binding.tvTitle.transitionName = "title_${job.id}"
        binding.tvCompanyName.transitionName = "company_name_${job.id}"
        binding.tvLocation.transitionName = "location_${job.id}"

        tvTitle.text = job.title
        tvCompanyName.text = job.companyName
        tvLocation.text = job.location

        binding.btnFavorite.isChecked = job.isFavorite

        binding.tvDescription.text =
            HtmlCompat.fromHtml(job.description, HtmlCompat.FROM_HTML_MODE_COMPACT)
    }

    private fun initListener(job : JobModel?) = with(binding) {
        btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        job?.let { job ->
            btnFavorite.setOnClickListener {
                viewModel.updateFavoriteStatus(job, binding.btnFavorite.isChecked)
            }

            btnApply.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = job.url.toUri()
                startActivity(intent)
            }
        }
    }

    private fun setupTransition() = with(window) {
        requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        sharedElementEnterTransition = AutoTransition()
        sharedElementExitTransition = AutoTransition()
        exitTransition = AutoTransition()
        enterTransition = AutoTransition()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}