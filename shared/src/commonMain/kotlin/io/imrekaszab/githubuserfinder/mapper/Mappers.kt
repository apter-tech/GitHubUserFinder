package io.imrekaszab.githubuserfinder.mapper

import io.imrekaszab.githubuserfinder.model.api.GitHubUserApiModel
import io.imrekaszab.githubuserfinder.model.api.GitHubUserDetailsApiModel
import io.imrekaszab.githubuserfinder.model.domain.GitHubUser
import io.imrekaszab.githubuserfinder.model.domain.GitHubUserDetails

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

fun GitHubUserDetailsApiModel.toDomain() =
    GitHubUserDetails(
        id = id,
        login = login,
        avatarUrl = avatar_url,
        name = name ?: login,
        company = company,
        blog = blog,
        location = location,
        email = email,
        bio = bio,
        twitterUsername = twitter_username,
        followers = followers,
        following = following,
        publicRepos = public_repos
    )
