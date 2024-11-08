package com.nalldev.home.data.util

import com.nalldev.core.utils.CommonHelper
import com.nalldev.home.data.model.DataItem
import com.nalldev.home.domain.model.JobModel

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
            date = CommonHelper.convertTimestampToDate(data.createdAt)
        )
    }
}