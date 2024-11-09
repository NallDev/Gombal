package com.nalldev.home.domain.model

data class JobModel(
    val id : String,
    val companyName : String,
    val title : String,
    val description : String,
    val remote : Boolean,
    val tags : List<String>,
    val location : String,
    val date : String,
    var isFavorite : Boolean = false
)