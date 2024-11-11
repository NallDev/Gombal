package com.nalldev.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class JobModel(
    val id : String,
    val companyName : String,
    val title : String,
    val description : String,
    val remote : Boolean,
    val tags : List<String>,
    val location : String,
    val date : String,
    val url : String,
    var isFavorite : Boolean = false
)  : Parcelable