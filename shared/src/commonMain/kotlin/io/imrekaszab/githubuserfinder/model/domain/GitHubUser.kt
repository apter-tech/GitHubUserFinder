package io.imrekaszab.githubuserfinder.model.domain

data class GitHubUser(
    val id: Int,
    val login: String,
    val avatarUrl: String,
    val followersUrl: String,
    val followingUrl: String,
    val reposUrl: String,
    val score: Double
)
