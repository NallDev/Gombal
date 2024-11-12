package com.nalldev.favorites

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.nalldev.core.domain.model.JobModel
import com.nalldev.core.utils.Constant
import com.nalldev.data.di.favoritesDataModule
import com.nalldev.domain.di.favoritesDomainModule
import com.nalldev.favorites.databinding.ActivityFavoritesBinding
import com.nalldev.favorites.databinding.ItemJobBinding
import com.nalldev.favorites.di.favoritesModule
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoritesActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFavoritesBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModel<FavoritesViewModel>()

    private val favoritesAdapterListener by lazy {
        object : FavoritesAdapter.Listener {
            override fun onItemClicked(job: JobModel, view: ItemJobBinding) {
                val uri = Uri.parse("gombal://detail")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                intent.putExtra(Constant.EXTRAS_DETAIL_KEY, job)

                view.tvTitle.transitionName = "title_${job.id}"
                view.tvCompanyName.transitionName = "company_name_${job.id}"
                view.tvLocation.transitionName = "location_${job.id}"

                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this@FavoritesActivity,
                    Pair(view.tvTitle, view.tvTitle.transitionName),
                    Pair(view.tvCompanyName, view.tvCompanyName.transitionName),
                    Pair(view.tvLocation, view.tvLocation.transitionName),
                )

                startActivity(intent, options.toBundle())
            }

            override fun onFavoriteClicked(job: JobModel) {
                viewModel.deleteFromFavorite(job)
            }
        }
    }

    private val favoritesAdapter by lazy {
        FavoritesAdapter(favoritesAdapterListener)
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
        loadKoinModules(listOf(favoritesDataModule, favoritesDomainModule, favoritesModule))

        initView()
        initObserver()
        initListener()

    }

    private fun initView() = with(binding) {
        rvFavorites.adapter = favoritesAdapter
    }

    private fun initObserver() = with(viewModel) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
               favoriteJobs.collect { jobs ->
                    favoritesAdapter.submitList(jobs)
                }
            }
        }
    }

    private fun initListener() = with(binding) {
        btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}