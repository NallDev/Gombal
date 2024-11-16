package com.nalldev.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.nalldev.home.databinding.ItemJobBinding
import com.nalldev.core.domain.model.JobModel

class JobAdapter(val listener : Listener? = null) : ListAdapter<JobModel, JobAdapter.ViewHolder>(this) {

    inner class ViewHolder(private val binding : ItemJobBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(job : JobModel) = with(binding) {
            tvTitle.text = job.title
            tvCompanyName.text = job.companyName
            tvLocation.text = job.location
            btnFavorite.isChecked = job.isFavorite

            binding.btnFavorite.setOnClickListener {
                listener?.onFavoriteClicked(job, binding.btnFavorite.isChecked)
            }

            binding.root.setOnClickListener {
                listener?.onItemClicked(job)
            }
        }

        fun createChip(job : JobModel) = with(binding) {
            if (job.remote) {
                job.tags.toMutableList().add(0,"Remote")
            }
            job.tags.forEach { tag ->
                val chip = Chip(binding.root.context)

                chip.setTextColor(binding.root.context.getColorStateList(com.nalldev.core.R.color.white))
                chip.setChipBackgroundColorResource(com.nalldev.core.R.color.colorPrimary)

                chip.chipStrokeColor = binding.root.context.getColorStateList(android.R.color.transparent)
                chip.text = tag

                cgTags.addView(chip)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(holder.adapterPosition))
        holder.createChip(getItem(holder.adapterPosition))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemJobBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        when(val latestPayload = payloads.lastOrNull()) {
            is JobAdapterChangePayload.DataChanged -> holder.bind(latestPayload.job)
            else -> onBindViewHolder(holder, holder.adapterPosition)
        }
    }

    interface Listener {
        fun onItemClicked(job : JobModel)
        fun onFavoriteClicked(job : JobModel, isFavorite : Boolean)
    }

    companion object : DiffUtil.ItemCallback<JobModel>() {
        override fun areItemsTheSame(oldItem: JobModel, newItem: JobModel): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: JobModel, newItem: JobModel): Boolean = oldItem == newItem

        override fun getChangePayload(oldItem: JobModel, newItem: JobModel): Any? {
            return if (oldItem != newItem) {
                JobAdapterChangePayload.DataChanged(newItem)
            } else {null}
        }
    }

    private sealed interface JobAdapterChangePayload {
        data class DataChanged(val job : JobModel) : JobAdapterChangePayload
    }
}