package io.imrekaszab.githubuserfinder.repository

import io.imrekaszab.githubuserfinder.api.GitHubApi
import io.imrekaszab.githubuserfinder.mapper.toDomain
import io.imrekaszab.githubuserfinder.mapper.toDomainModels
import io.imrekaszab.githubuserfinder.model.domain.GitHubUser
import io.imrekaszab.githubuserfinder.model.domain.GitHubUserDetails
import io.imrekaszab.githubuserfinder.util.ApplicationDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.withContext

class GitHubUserRepositoryImpl(private val gitHubApi: GitHubApi): GitHubUserRepository {
    private val gitHubUserListStateFlow = MutableStateFlow<List<GitHubUser>>(emptyList())
    private val gitHubUserDetailsStateFlow = MutableStateFlow<GitHubUserDetails?>(null)

    override suspend fun searchUser(userName: String) = withContext(ApplicationDispatcher) {
        try {
            val result = gitHubApi.searchUser(userName)
            gitHubUserListStateFlow.value = result.items.toDomainModels()
        } catch (exception: Exception) {
            gitHubUserListStateFlow.value = emptyList()
        }
    }

    override suspend fun refreshUserDetails(userName: String) {
        try {
            val result = gitHubApi.refreshUserDetails(userName).toDomain()
            gitHubUserDetailsStateFlow.value = result
        } catch (exception: Exception) {
            // TODO handle error
        }
    }

    override fun getUsers(): Flow<List<GitHubUser>> = gitHubUserListStateFlow

    override fun getUserDetails(): Flow<GitHubUserDetails> = gitHubUserDetailsStateFlow
        .filterNotNull()
}
