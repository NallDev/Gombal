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

class DetailActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupTransition()
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

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

        job?.let {
            btnFavorite.setOnClickListener {

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
}