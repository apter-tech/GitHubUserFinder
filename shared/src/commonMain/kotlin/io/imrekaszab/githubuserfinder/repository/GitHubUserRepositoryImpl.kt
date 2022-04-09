package io.imrekaszab.githubuserfinder.repository

import io.imrekaszab.githubuserfinder.api.GitHubApi
import io.imrekaszab.githubuserfinder.mapper.toDomainModels
import io.imrekaszab.githubuserfinder.model.domain.GitHubUser
import io.imrekaszab.githubuserfinder.util.ApplicationDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

class GitHubUserRepositoryImpl(private val gitHubApi: GitHubApi): GitHubUserRepository {
    private val gitHubUserListStateFlow = MutableStateFlow<List<GitHubUser>>(emptyList())

    override suspend fun searchUser(userName: String) = withContext(ApplicationDispatcher) {
        try {
            val result = gitHubApi.searchUser(userName)
            gitHubUserListStateFlow.value = result.items.toDomainModels()
        } catch (exception: Exception) {
            gitHubUserListStateFlow.value = emptyList()
        }
    }

    override fun getUsers(): Flow<List<GitHubUser>> = gitHubUserListStateFlow
}
