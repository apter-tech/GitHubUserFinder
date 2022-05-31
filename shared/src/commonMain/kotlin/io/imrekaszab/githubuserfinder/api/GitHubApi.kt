package io.imrekaszab.githubuserfinder.api

import io.imrekaszab.githubuserfinder.model.api.GitHubUserDetailsApiModel
import io.imrekaszab.githubuserfinder.model.api.SearchResponse

interface GitHubApi {
    suspend fun searchUser(userName: String, page: Int): SearchResponse
    suspend fun refreshUserDetails(userName: String): GitHubUserDetailsApiModel
}
