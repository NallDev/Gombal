package com.nalldev.home.data.util

import com.nalldev.core.data.local.room.model.JobEntity
import com.nalldev.core.data.local.room.model.JobFavoritesEntity
import com.nalldev.core.utils.CommonHelper
import com.nalldev.home.data.model.DataItem
import com.nalldev.home.domain.model.JobModel

object JobMapper {
    fun toDomain(data: JobEntity) : JobModel {
        return JobModel (
            id = data.id,
            companyName = data.companyName,
            title = data.title,
            description = data.description,
            remote = data.remote,
            tags = data.tags,
            location = data.location,
            date = data.date
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
            date = job.date
        )
    }

    fun networkToDb(data: DataItem) : JobEntity {
        return JobEntity (
            id = data.slug,
            companyName = data.companyName ?: "Unknown",
            title = data.title,
            description = data.description ?: "-",
            remote = data.remote,
            tags = data.tags,
            location = data.location ?: "-",
            date = CommonHelper.convertTimestampToDate(data.createdAt)
        )
    }
}