package io.imrekaszab.githubuserfinder.repository

import io.imrekaszab.githubuserfinder.api.GitHubApi
import io.imrekaszab.githubuserfinder.mapper.toDomain
import io.imrekaszab.githubuserfinder.mapper.toDomainModels
import io.imrekaszab.githubuserfinder.model.domain.GitHubPagingInfo
import io.imrekaszab.githubuserfinder.model.domain.GitHubUser
import io.imrekaszab.githubuserfinder.model.domain.GitHubUserDetails
import io.imrekaszab.githubuserfinder.util.ApplicationDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class GitHubUserRepositoryImpl(private val gitHubApi: GitHubApi): GitHubUserRepository {
    private val gitHubUserListStateFlow = MutableStateFlow<MutableList<GitHubUser>>(mutableListOf())
    private val gitHubUserDetailsStateFlow = MutableStateFlow<GitHubUserDetails?>(null)
    private val gitHubPagingInfoStateFlow = MutableStateFlow(GitHubPagingInfo())

    override suspend fun searchUser(userName: String) = withContext(ApplicationDispatcher) {
        try {
            val result = gitHubApi.searchUser(userName, 1)
            gitHubUserListStateFlow.value = result.items.toDomainModels().toMutableList()
            gitHubPagingInfoStateFlow.value = GitHubPagingInfo(
                totalItemCount = result.total_count,
                currentCount = result.items.size,
                userName = userName
            )
        } catch (exception: Exception) {
            gitHubUserListStateFlow.value = mutableListOf()
        }
    }

    override suspend fun requestNextPage() {
        try {
            val pagingInfo = gitHubPagingInfoStateFlow.value
            if (pagingInfo.currentCount < pagingInfo.totalItemCount) {
                val nextPage = pagingInfo.page + 1
                val result = gitHubApi.searchUser(pagingInfo.userName, nextPage)
                val currentResults = gitHubUserListStateFlow.value
                currentResults.addAll(result.items.toDomainModels())
                gitHubUserListStateFlow.value = currentResults
                gitHubPagingInfoStateFlow.value =
                    pagingInfo.copy(currentCount = currentResults.size, page = nextPage)
            }
        } catch (exception: Exception) {
            // TODO handle error
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

    override fun isFetchingFinished(): Flow<Boolean> = gitHubPagingInfoStateFlow
        .map { it.currentCount == it.totalItemCount }
}
