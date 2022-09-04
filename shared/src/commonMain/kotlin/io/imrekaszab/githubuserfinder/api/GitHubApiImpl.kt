package io.imrekaszab.githubuserfinder.api

import io.imrekaszab.githubuserfinder.model.api.GitHubUserDetailsApiModel
import io.imrekaszab.githubuserfinder.model.api.SearchResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class GitHubApiImpl(
    private val httpClient: HttpClient
) : GitHubApi {
    override suspend fun searchUser(userName: String, page: Int, offset: Int): SearchResponse =
        httpClient.get("search/users?q=$userName&page=$page&per_page=$offset").body()

    override suspend fun refreshUserDetails(userName: String): GitHubUserDetailsApiModel =
        httpClient.get("users/$userName").body()
}
