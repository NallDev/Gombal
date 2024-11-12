package com.nalldev.data.utils

import com.nalldev.core.data.local.room.model.JobFavoritesEntity
import com.nalldev.core.domain.model.JobModel

object JobMapper {
    fun toDomain(entity: JobFavoritesEntity) = JobModel(
        id = entity.id,
        companyName = entity.companyName,
        title = entity.title,
        url = entity.url,
        description = entity.description,
        tags = entity.tags,
        remote = entity.remote,
        location = entity.location,
        date = entity.date,
        isFavorite = true
    )
}