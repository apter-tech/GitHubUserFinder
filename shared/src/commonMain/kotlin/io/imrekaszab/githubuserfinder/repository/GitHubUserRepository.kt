package io.imrekaszab.githubuserfinder.repository

import io.imrekaszab.githubuserfinder.model.domain.GitHubUser
import io.imrekaszab.githubuserfinder.model.domain.GitHubUserDetails
import kotlinx.coroutines.flow.Flow

interface GitHubUserRepository {
    suspend fun searchUser(userName: String)
    suspend fun requestNextPage()
    suspend fun refreshUserDetails(userName: String)
    fun getUsers(): Flow<List<GitHubUser>>
    fun getUserDetails(): Flow<GitHubUserDetails>
    fun isFetchingFinished(): Flow<Boolean>
}
