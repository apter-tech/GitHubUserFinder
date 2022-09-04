package io.imrekaszab.githubuserfinder.service

import co.touchlab.kermit.Logger
import io.imrekaszab.githubuserfinder.action.GitHubUserAction
import io.imrekaszab.githubuserfinder.di.injectLogger
import io.imrekaszab.githubuserfinder.model.domain.GitHubPagingInfo
import io.imrekaszab.githubuserfinder.model.domain.GitHubUser
import io.imrekaszab.githubuserfinder.model.domain.GitHubUserDetails
import io.imrekaszab.githubuserfinder.repository.GitHubUserRepository
import io.imrekaszab.githubuserfinder.store.GitHubUserStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GitHubUserService : GitHubUserAction, GitHubUserStore, KoinComponent {
    private val logger: Logger by injectLogger("GitHubUserService")
    private val gitHubUserRepository: GitHubUserRepository by inject()

    private val gitHubUserDetailsStateFlow = MutableStateFlow<GitHubUserDetails?>(null)
    private val gitHubUserListStateFlow = MutableStateFlow<List<GitHubUser>>(listOf())
    private val gitHubPagingInfoStateFlow = MutableStateFlow(GitHubPagingInfo())
    private var fetchingInProgress = false

    override suspend fun searchUser(userName: String) = withContext(Dispatchers.Default) {
        try {
            val pagingInfo = GitHubPagingInfo()
            val result =
                gitHubUserRepository.fetchPage(userName, pagingInfo.page, pagingInfo.offset)
            gitHubUserListStateFlow.emit(result.second)
            gitHubPagingInfoStateFlow.emit(result.first.copy(currentCount = result.second.size))
        } catch (exception: Exception) {
            gitHubUserListStateFlow.emit(mutableListOf())
            Logger.e("Error happened: $exception")
            throw exception
        }
    }

    override suspend fun fetchNextPage() = withContext(Dispatchers.Default) {
        try {
            if (!fetchingInProgress && !isFetchingFinished().first()) {
                val pagingInfo = gitHubPagingInfoStateFlow.first()
                fetchingInProgress = true
                val result = gitHubUserRepository.fetchPage(
                    pagingInfo.userName,
                    pagingInfo.page + 1,
                    pagingInfo.offset
                )
                val currentResults = getUsers().first().toMutableList()
                currentResults.addAll(result.second)
                gitHubUserListStateFlow.emit(currentResults.distinctBy { it.id })
                gitHubPagingInfoStateFlow.emit(
                    result.first.copy(currentCount = currentResults.size)
                )
                fetchingInProgress = false
            }
        } catch (exception: Exception) {
            gitHubUserListStateFlow.emit(mutableListOf())
            logger.e("Error happened: $exception")
            throw exception
        }
    }

    override suspend fun refreshUserDetails(userName: String) =
        withContext(Dispatchers.Default) {
            try {
                val result = gitHubUserRepository.getUserDetails(userName)
                gitHubUserDetailsStateFlow.emit(result)
            } catch (exception: Exception) {
                logger.e("Error happened: $exception")
                throw exception
            }
        }

    override fun getUsers(): Flow<List<GitHubUser>> = gitHubUserListStateFlow

    override fun getUserDetails(): Flow<GitHubUserDetails> =
        gitHubUserDetailsStateFlow
            .filterNotNull()

    override fun isFetchingFinished(): Flow<Boolean> =
        gitHubPagingInfoStateFlow
            .map { it.currentCount == it.totalItemCount }
}
