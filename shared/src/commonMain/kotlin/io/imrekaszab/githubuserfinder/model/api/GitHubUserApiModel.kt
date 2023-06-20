package io.imrekaszab.githubuserfinder.model.api

import kotlinx.serialization.Serializable

@Suppress("ConstructorParameterNaming")
@Serializable
data class GitHubUserApiModel(
    val id: Int,
    val login: String,
    val avatar_url: String,
    val followers_url: String,
    val following_url: String,
    val repos_url: String,
    val score: Double
)
