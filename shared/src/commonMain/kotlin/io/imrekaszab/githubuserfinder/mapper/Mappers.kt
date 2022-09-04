package io.imrekaszab.githubuserfinder.mapper

import io.imrekaszab.githubuserfinder.db.GitHubUserDataModel
import io.imrekaszab.githubuserfinder.model.api.GitHubUserApiModel
import io.imrekaszab.githubuserfinder.model.api.GitHubUserDetailsApiModel
import io.imrekaszab.githubuserfinder.model.domain.GitHubUser

fun List<GitHubUserApiModel>.toDomainModels() =
    map { it.toDomain() }

fun GitHubUserApiModel.toDomain() =
    GitHubUser(
        id = id,
        login = login,
        avatarUrl = avatar_url,
        score = score
    )

fun GitHubUserDetailsApiModel.toDomain() =
    GitHubUser(
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

fun GitHubUserDataModel.toDomain() =
    GitHubUser(
        id = id.toInt(),
        login = login,
        avatarUrl = avatarUrl,
        score = score,
        name = name,
        company = company,
        blog = blog,
        location = location,
        email = email,
        bio = bio,
        twitterUsername = twitterUsername,
        followers = followers.toInt(),
        following = following.toInt(),
        publicRepos = publicRepos.toInt()
    )

fun GitHubUser.toData() = GitHubUserDataModel(
    id = id.toLong(),
    login = login,
    avatarUrl = avatarUrl,
    score = score,
    name = name,
    company = company,
    blog = blog,
    location = location,
    email = email,
    bio = bio,
    twitterUsername = twitterUsername,
    followers = followers.toLong(),
    following = following.toLong(),
    publicRepos = publicRepos.toLong(),
)

fun List<GitHubUserDataModel>.toDomains() = map { it.toDomain() }
