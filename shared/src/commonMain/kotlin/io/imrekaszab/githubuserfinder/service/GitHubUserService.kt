package io.imrekaszab.githubuserfinder.service

import co.touchlab.kermit.Logger
import io.imrekaszab.githubuserfinder.di.injectLogger
import io.imrekaszab.githubuserfinder.model.domain.GitHubPagingInfo
import io.imrekaszab.githubuserfinder.model.domain.GitHubUser
import io.imrekaszab.githubuserfinder.repository.GitHubUserRepository
import io.imrekaszab.githubuserfinder.service.action.GitHubUserAction
import io.imrekaszab.githubuserfinder.service.store.GitHubUserStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent

class GitHubUserService(
    private val gitHubUserRepository: GitHubUserRepository
) : GitHubUserAction, GitHubUserStore, KoinComponent {
    private val logger: Logger by injectLogger("GitHubUserService")

    private val gitHubUserDetailsStateFlow = MutableStateFlow<GitHubUser?>(null)
    private val gitHubUserListStateFlow = MutableStateFlow<List<GitHubUser>>(listOf())
    private val gitHubPagingInfoStateFlow = MutableStateFlow(GitHubPagingInfo())
    private val gitHubUserDetails: Flow<GitHubUser?> = gitHubUserDetailsStateFlow
    private var fetchingInProgress = false

    override suspend fun searchUser(userName: String) = withContext(Dispatchers.Default) {
        try {
            val pagingInfo = GitHubPagingInfo()
            val result = gitHubUserRepository.searchUser(userName, pagingInfo.page, pagingInfo.offset)
            val userList = result.second

            val newPagingInfo = pagingInfo.copy(
                totalItemCount = result.first,
                userName = userName,
                currentCount = userList.size
            )
            gitHubUserListStateFlow.emit(userList)
            gitHubPagingInfoStateFlow.emit(newPagingInfo)
        } catch (@Suppress("TooGenericExceptionCaught") exception: Exception) {
            gitHubUserListStateFlow.emit(mutableListOf())
            logger.e("searchUser, error happened $exception")
            throw exception
        }
    }

    override suspend fun fetchNextPage() = withContext(Dispatchers.Default) {
        try {
            if (!fetchingInProgress && !isFetchingFinished().first()) {
                val pagingInfo = gitHubPagingInfoStateFlow.first()
                fetchingInProgress = true
                val result = gitHubUserRepository.searchUser(
                    pagingInfo.userName,
                    pagingInfo.page + 1,
                    pagingInfo.offset
                )
                val userList = result.second

                val currentResults = getUsers().first().toMutableList()
                currentResults.addAll(userList)
                gitHubUserListStateFlow.emit(currentResults.distinctBy { it.id })
                gitHubPagingInfoStateFlow.emit(
                    pagingInfo.copy(
                        totalItemCount = result.first,
                        currentCount = currentResults.size,
                        page = pagingInfo.page + 1
                    )
                )
                fetchingInProgress = false
            }
        } catch (@Suppress("TooGenericExceptionCaught") exception: Exception) {
            gitHubUserListStateFlow.emit(mutableListOf())
            logger.e("fetchNextPage, error happened $exception")
            throw exception
        }
    }

    override suspend fun refreshUserDetails(userName: String) = withContext(Dispatchers.Default) {
        try {
            val result = gitHubUserRepository.refreshUserDetails(userName)
            logger.i("Result: $result")
            gitHubUserDetailsStateFlow.emit(result)
        } catch (@Suppress("TooGenericExceptionCaught") exception: Exception) {
            logger.e("refreshUserDetails, error happened $exception")
            throw exception
        }
    }

    override suspend fun saveUser() = withContext(Dispatchers.Default) {
        val user = getUserDetails().first() ?: return@withContext
        gitHubUserRepository.saveUser(user)
        gitHubUserDetailsStateFlow.emit(user.copy(favourite = true))
    }

    override suspend fun deleteUser() = withContext(Dispatchers.Default) {
        val user = getUserDetails().first() ?: return@withContext
        gitHubUserRepository.deleteUser(user.id)
        gitHubUserDetailsStateFlow.emit(user.copy(favourite = false))
    }

    override suspend fun deleteAllUsers() = withContext(Dispatchers.Default) {
        gitHubUserRepository.deleteAllUsers()
    }

    override fun getUsers(): Flow<List<GitHubUser>> = gitHubUserListStateFlow
        .combine(getSavedUsers()) { currentList, savedList ->
            currentList.map { item -> item.copy(favourite = savedList.any { it.id == item.id }) }
        }

    override fun getUserDetails(): Flow<GitHubUser?> =
        gitHubUserDetails
            .combine(gitHubUserRepository.getSavedUserList()) { currentUserDetail, savedUserList ->
                logger.i(
                    "User details params " +
                        "current: $currentUserDetail " +
                        "savedUserList: $savedUserList"
                )
                savedUserList.firstOrNull { it.id == currentUserDetail?.id } ?: currentUserDetail
            }

    override fun isFetchingFinished(): Flow<Boolean> =
        gitHubPagingInfoStateFlow
            .map {
                println("isFetchingFinished pisa: ${it.currentCount} == ${it.totalItemCount}")
                it.currentCount == it.totalItemCount
            }

    override fun getSavedUsers(): Flow<List<GitHubUser>> =
        gitHubUserRepository.getSavedUserList()
}
