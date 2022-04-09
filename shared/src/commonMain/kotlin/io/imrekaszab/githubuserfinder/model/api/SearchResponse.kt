package io.imrekaszab.githubuserfinder.model.api

import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    val total_count: Int = 0,
    val items: List<GitHubUserApiModel> = emptyList()
)
