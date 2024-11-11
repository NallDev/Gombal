package com.nalldev.home.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JobApiResponse(
	val data: List<DataItem> = mutableListOf(),
	val meta: Meta? = null,
	val links: Links? = null
)

@Serializable
data class Links(
	val next: String? = null,
	val first: String? = null
)

@Serializable
data class DataItem(
	@SerialName("job_types")
	val jobTypes: List<String> = mutableListOf(),
	@SerialName("company_name")
	val companyName: String? = null,
	val description: String? = null,
	@SerialName("created_at")
	val createdAt: Int? = null,
	val location: String? = null,
	val title: String,
	val remote: Boolean,
	val slug: String,
	val url: String,
	val tags: List<String> = mutableListOf()
)

@Serializable
data class Meta(
	val path: String? = null,
	val perPage: Int? = null,
	val terms: String? = null,
	val from: Int? = null,
	val to: Int? = null,
	val currentPage: Int? = null,
	val info: String? = null
)