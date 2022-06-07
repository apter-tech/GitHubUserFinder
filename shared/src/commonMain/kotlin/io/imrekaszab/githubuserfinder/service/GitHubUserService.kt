package io.imrekaszab.githubuserfinder.service

import co.touchlab.kermit.Logger
import io.imrekaszab.githubuserfinder.action.GitHubUserAction
import io.imrekaszab.githubuserfinder.model.domain.GitHubPagingInfo
import io.imrekaszab.githubuserfinder.model.domain.GitHubUser
import io.imrekaszab.githubuserfinder.model.domain.GitHubUserDetails
import io.imrekaszab.githubuserfinder.repository.GitHubUserRepository
import io.imrekaszab.githubuserfinder.store.GitHubUserStore
import io.imrekaszab.githubuserfinder.util.ApplicationDispatcher
import io.imrekaszab.githubuserfinder.util.CommonFlow
import io.imrekaszab.githubuserfinder.util.asCommonFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GitHubUserService : GitHubUserAction, GitHubUserStore, KoinComponent {
    private val gitHubUserRepository: GitHubUserRepository by inject()

    private val gitHubUserDetailsStateFlow = MutableStateFlow<GitHubUserDetails?>(null)
    private val gitHubUserListStateFlow = MutableStateFlow<MutableList<GitHubUser>>(mutableListOf())
    private val gitHubPagingInfoStateFlow = MutableStateFlow(GitHubPagingInfo())
    private var fetchingInProgress = false

    @Throws(RuntimeException::class)
    override suspend fun searchUser(userName: String) = withContext(ApplicationDispatcher) {
        try {
            gitHubPagingInfoStateFlow.value = GitHubPagingInfo()
            val result = gitHubUserRepository.fetchPage(userName, 1)
            gitHubUserListStateFlow.value = result.second.toMutableList()
            gitHubPagingInfoStateFlow.value = result.first.copy(currentCount = result.second.size)
        } catch (exception: Exception) {
            gitHubUserListStateFlow.value = mutableListOf()
            Logger.e("Error happened: $exception")
            throw exception
        }
    }

    @Throws(RuntimeException::class)
    override suspend fun fetchNextPage() = withContext(ApplicationDispatcher) {
        try {
            val pagingInfo = gitHubPagingInfoStateFlow.value
            if (!fetchingInProgress && pagingInfo.currentCount < pagingInfo.totalItemCount) {
                fetchingInProgress = true
                val nextPage = pagingInfo.page + 1
                val result = gitHubUserRepository.fetchPage(pagingInfo.userName, nextPage)
                val currentResults = gitHubUserListStateFlow.value
                currentResults.addAll(result.second)
                gitHubUserListStateFlow.value = currentResults
                gitHubPagingInfoStateFlow.value =
                    pagingInfo.copy(currentCount = currentResults.size, page = nextPage)
                fetchingInProgress = false
            }
        } catch (exception: Exception) {
            Logger.e("Error happened: $exception")
            throw exception
        }
    }

    @Throws(RuntimeException::class)
    override suspend fun refreshUserDetails(userName: String) =
        withContext(ApplicationDispatcher) {
            try {
                val result = gitHubUserRepository.getUserDetails(userName)
                gitHubUserDetailsStateFlow.value = result
            } catch (exception: Exception) {
                Logger.e("Error happened: $exception")
                throw exception
            }
        }

    override fun getUsers(): CommonFlow<List<GitHubUser>> = gitHubUserListStateFlow.asCommonFlow()

    override fun getUserDetails(): CommonFlow<GitHubUserDetails> =
        gitHubUserDetailsStateFlow
            .filterNotNull()
            .asCommonFlow()

    override fun isFetchingFinished(): CommonFlow<Boolean> =
        gitHubPagingInfoStateFlow
            .map { it.currentCount == it.totalItemCount }
            .asCommonFlow()
}
