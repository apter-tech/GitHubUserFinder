package io.imrekaszab.githubuserfinder.api

import io.imrekaszab.githubuserfinder.model.api.SearchResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.*

class GitHubApiImpl(
    private val httpClient: HttpClient
) : GitHubApi {
    override suspend fun searchUser(userName: String): SearchResponse =
        httpClient.get {
            url {
                encodedPath = "search/users?q=$userName"
            }
        }
}
