package io.imrekaszab.githubuserfinder.service

import co.touchlab.kermit.Logger
import io.imrekaszab.githubuserfinder.action.GitHubUserAction
import io.imrekaszab.githubuserfinder.api.GitHubApi
import io.imrekaszab.githubuserfinder.di.injectLogger
import io.imrekaszab.githubuserfinder.mapper.toDomain
import io.imrekaszab.githubuserfinder.mapper.toDomainModels
import io.imrekaszab.githubuserfinder.model.domain.GitHubPagingInfo
import io.imrekaszab.githubuserfinder.model.domain.GitHubUser
import io.imrekaszab.githubuserfinder.repository.GitHubUserRepository
import io.imrekaszab.githubuserfinder.store.GitHubUserStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GitHubUserService : GitHubUserAction, GitHubUserStore, KoinComponent {
    private val logger: Logger by injectLogger("GitHubUserService")
    private val gitHubApi: GitHubApi by inject()
    private val gitHubUserRepository: GitHubUserRepository by inject()

    private val gitHubUserDetailsStateFlow = MutableStateFlow<GitHubUser?>(null)
    private val gitHubUserListStateFlow = MutableStateFlow<List<GitHubUser>>(listOf())
    private val gitHubPagingInfoStateFlow = MutableStateFlow(GitHubPagingInfo())
    private var fetchingInProgress = false

    override suspend fun searchUser(userName: String) = withContext(Dispatchers.Default) {
        try {
            val pagingInfo = GitHubPagingInfo()
            val result = gitHubApi.searchUser(userName, pagingInfo.page, pagingInfo.offset)
            val userList = result.items.toDomainModels().toMutableList()

            val newPagingInfo =
                pagingInfo.copy(
                    totalItemCount = result.total_count,
                    userName = userName,
                    currentCount = userList.size
                )
            gitHubUserListStateFlow.emit(userList)
            gitHubPagingInfoStateFlow.emit(newPagingInfo)
        } catch (exception: Exception) {
            gitHubUserListStateFlow.emit(mutableListOf())
            logger.e("Error happened: $exception")
            throw exception
        }
    }

    override suspend fun fetchNextPage() = withContext(Dispatchers.Default) {
        try {
            if (!fetchingInProgress && !isFetchingFinished().first()) {
                val pagingInfo = gitHubPagingInfoStateFlow.first()
                fetchingInProgress = true
                val result = gitHubApi.searchUser(
                    pagingInfo.userName,
                    pagingInfo.page + 1,
                    pagingInfo.offset
                )
                val userList = result.items.toDomainModels().toMutableList()

                val currentResults = getUsers().first().toMutableList()
                currentResults.addAll(userList)
                gitHubUserListStateFlow.emit(currentResults.distinctBy { it.id })
                gitHubPagingInfoStateFlow.emit(
                    pagingInfo.copy(currentCount = currentResults.size, page = pagingInfo.page + 1)
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
                val result = gitHubApi.refreshUserDetails(userName).toDomain()
                gitHubUserDetailsStateFlow.emit(result)
            } catch (exception: Exception) {
                logger.e("Error happened: $exception")
                throw exception
            }
        }

    override suspend fun saveUser() = withContext(Dispatchers.Default) {
        val user = gitHubUserDetailsStateFlow.first() ?: return@withContext
        gitHubUserRepository.saveUser(user)
    }

    override suspend fun deleteUser() = withContext(Dispatchers.Default) {
        val user = gitHubUserDetailsStateFlow.first() ?: return@withContext
        gitHubUserRepository.deleteUser(user.id)
    }

    override suspend fun deleteAllUser() = withContext(Dispatchers.Default) {
        gitHubUserRepository.deleteAll()
    }

    override fun getUsers(): Flow<List<GitHubUser>> = gitHubUserListStateFlow

    override fun getUserDetails(): Flow<GitHubUser> =
        gitHubUserDetailsStateFlow
            .combine(gitHubUserRepository.getSavedUserList()) { currentUserDetail, savedUserList ->
                val savedUser = savedUserList.firstOrNull { it.id == currentUserDetail?.id }
                return@combine savedUser ?: currentUserDetail
            }
            .filterNotNull()

    override fun isFetchingFinished(): Flow<Boolean> =
        gitHubPagingInfoStateFlow
            .map { it.currentCount == it.totalItemCount }

    override fun getSavedUsers(): Flow<List<GitHubUser>> =
        gitHubUserRepository.getSavedUserList()
}
