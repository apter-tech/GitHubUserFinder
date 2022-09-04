package io.imrekaszab.githubuserfinder.model.domain

data class GitHubPagingInfo(
    val totalItemCount: Int = 0,
    val currentCount: Int = 0,
    val offset: Int = 100,
    val page: Int = 1,
    val userName: String = ""
)
