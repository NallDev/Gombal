package com.nalldev.core.data.local.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jobs")
data class JobEntity(
    @PrimaryKey
    val id: String,
    val companyName : String,
    val title : String,
    val description : String,
    val remote : Boolean,
    val tags : List<String>,
    val location : String,
    val date : String
)