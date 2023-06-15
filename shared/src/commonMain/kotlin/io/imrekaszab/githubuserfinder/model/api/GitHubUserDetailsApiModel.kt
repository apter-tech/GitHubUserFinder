package io.imrekaszab.githubuserfinder.model.api

import kotlinx.serialization.Serializable

@Suppress("ConstructorParameterNaming")
@Serializable
data class GitHubUserDetailsApiModel(
    val id: Int,
    val login: String,
    val avatar_url: String,
    val followers_url: String,
    val following_url: String,
    val repos_url: String,
    val name: String?,
    val company: String?,
    val blog: String?,
    val location: String?,
    val email: String?,
    val bio: String?,
    val twitter_username: String?,
    val followers: Int,
    val following: Int,
    val public_repos: Int
)
