package io.imrekaszab.githubuserfinder.mapper

import io.imrekaszab.githubuserfinder.model.api.GitHubUserApiModel
import io.imrekaszab.githubuserfinder.model.domain.GitHubUser

fun List<GitHubUserApiModel>.toDomainModels() =
    map { it.toDomain() }

fun GitHubUserApiModel.toDomain() =
    GitHubUser(
        id = id,
        login = login,
        avatarUrl = avatar_url,
        followersUrl = followers_url,
        followingUrl = following_url,
        reposUrl = repos_url,
        score = score
    )
