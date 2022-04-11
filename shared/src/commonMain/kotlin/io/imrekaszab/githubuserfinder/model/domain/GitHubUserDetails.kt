package io.imrekaszab.githubuserfinder.model.domain

import kotlinx.serialization.Serializable

@Serializable
data class GitHubUserDetails(
    val id: Int,
    val login: String,
    val avatarUrl: String,
    val name: String,
    val company: String?,
    val blog: String?,
    val location: String?,
    val email: String?,
    val bio: String?,
    val twitterUsername: String?,
    val followers: Int,
    val following: Int,
    val publicRepos: Int
)
