package io.imrekaszab.githubuserfinder.repository

import io.imrekaszab.githubuserfinder.model.domain.GitHubPagingInfo
import io.imrekaszab.githubuserfinder.model.domain.GitHubUser
import io.imrekaszab.githubuserfinder.model.domain.GitHubUserDetails

interface GitHubUserRepository {
    suspend fun fetchPage(userName: String, page: Int): Pair<GitHubPagingInfo, List<GitHubUser>>
    suspend fun getUserDetails(userName: String): GitHubUserDetails
}
