package io.imrekaszab.githubuserfinder.api

import io.imrekaszab.githubuserfinder.model.api.GitHubUserDetailsApiModel
import io.imrekaszab.githubuserfinder.model.api.SearchResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.*

class GitHubApiImpl(
    private val httpClient: HttpClient
) : GitHubApi {
    override suspend fun searchUser(userName: String, page: Int): SearchResponse =
        httpClient.get {
            url {
                encodedPath = "search/users?q=$userName&page=$page&per_page=50"
            }
        }

    override suspend fun refreshUserDetails(userName: String): GitHubUserDetailsApiModel =
        httpClient.get {
            url {
                encodedPath = "users/$userName"
            }
        }
}
