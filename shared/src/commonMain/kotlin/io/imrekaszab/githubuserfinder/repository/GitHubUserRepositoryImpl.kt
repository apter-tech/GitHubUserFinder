package io.imrekaszab.githubuserfinder.repository

import io.imrekaszab.githubuserfinder.api.GitHubApi
import io.imrekaszab.githubuserfinder.mapper.toDomain
import io.imrekaszab.githubuserfinder.mapper.toDomainModels
import io.imrekaszab.githubuserfinder.model.domain.GitHubPagingInfo
import io.imrekaszab.githubuserfinder.model.domain.GitHubUser
import io.imrekaszab.githubuserfinder.model.domain.GitHubUserDetails

class GitHubUserRepositoryImpl(private val gitHubApi: GitHubApi) : GitHubUserRepository {

    override suspend fun fetchPage(
        userName: String,
        page: Int
    ): Pair<GitHubPagingInfo, List<GitHubUser>> {
        val result = gitHubApi.searchUser(userName, page)
        val userList = result.items.toDomainModels().toMutableList()
        val pagingInfo = GitHubPagingInfo(
            totalItemCount = result.total_count,
            userName = userName
        )

        return pagingInfo to userList
    }

    override suspend fun getUserDetails(userName: String): GitHubUserDetails =
        gitHubApi.refreshUserDetails(userName).toDomain()
}
