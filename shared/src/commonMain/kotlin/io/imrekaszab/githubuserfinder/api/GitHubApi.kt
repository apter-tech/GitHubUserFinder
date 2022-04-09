package io.imrekaszab.githubuserfinder.api

import io.imrekaszab.githubuserfinder.model.api.SearchResponse

interface GitHubApi {
    suspend fun searchUser(userName: String): SearchResponse
}