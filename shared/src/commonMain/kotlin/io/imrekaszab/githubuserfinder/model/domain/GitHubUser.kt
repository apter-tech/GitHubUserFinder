package io.imrekaszab.githubuserfinder.model.domain

data class GitHubUser(
    val id: Int,
    val login: String = "",
    val avatarUrl: String = "",
    val score: Double = 0.0,
    val name: String = "",
    val company: String? = null,
    val blog: String? = null,
    val location: String? = null,
    val email: String? = null,
    val bio: String? = null,
    val twitterUsername: String? = null,
    val followers: Int = 0,
    val following: Int = 0,
    val publicRepos: Int = 0,
    val favourite: Boolean = false
)
