package com.nalldev.home.data.util

import com.nalldev.core.data.local.room.model.JobFavoritesEntity
import com.nalldev.core.utils.CommonHelper
import com.nalldev.home.data.model.DataItem
import com.nalldev.core.domain.model.JobModel

object JobMapper {
    fun toDomain(data: DataItem) : JobModel {
        return JobModel (
            id = data.slug,
            companyName = data.companyName ?: "Unknown",
            title = data.title,
            description = data.description ?: "-",
            remote = data.remote,
            tags = data.tags,
            location = data.location ?: "-",
            date = CommonHelper.convertTimestampToDate(data.createdAt),
            url = data.url
        )
    }

    fun toJobFavoritesData(job : JobModel) : JobFavoritesEntity {
        return JobFavoritesEntity(
            id = job.id,
            companyName = job.companyName,
            title = job.title,
            description = job.description,
            remote = job.remote,
            tags = job.tags,
            location = job.location,
            date = job.date,
            url = job.url
        )
    }
}