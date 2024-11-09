package com.nalldev.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.nalldev.home.databinding.ItemJobBinding
import com.nalldev.home.domain.model.JobModel

class JobAdapter : PagingDataAdapter<JobModel, JobAdapter.ViewHolder>(this) {

    inner class ViewHolder(private val binding : ItemJobBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(job : JobModel) = with(binding) {
            tvTitle.text = job.title
            tvCompanyName.text = job.companyName
            tvLocation.text = job.location

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
        getItem(holder.bindingAdapterPosition)?.let { job ->
            holder.bind(job)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemJobBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    companion object : DiffUtil.ItemCallback<JobModel>() {
        override fun areItemsTheSame(oldItem: JobModel, newItem: JobModel): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: JobModel, newItem: JobModel): Boolean = oldItem == newItem
    }
}