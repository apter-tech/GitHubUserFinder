package io.imrekaszab.githubuserfinder.viewmodel

import io.imrekaszab.githubuserfinder.action.GitHubUserAction
import io.imrekaszab.githubuserfinder.store.GitHubUserStore
import io.imrekaszab.githubuserfinder.util.CommonViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GitHubUserListViewModel : CommonViewModel(), KoinComponent {
    private val gitHubUserAction: GitHubUserAction by inject()
    private val gitHubUserStore: GitHubUserStore by inject()

    private val isLoadingStateFlow = MutableStateFlow(false)

    val isLoading: Flow<Boolean> = isLoadingStateFlow
    val isFetchingFinished = gitHubUserStore.isFetchingFinished()
    val users = gitHubUserStore.getUsers()

    fun searchUser(userName: String? = null) {
        userName ?: return
        mainScope.launch {
            try {
                errorStateFlow.value = null
                isLoadingStateFlow.value = true
                gitHubUserAction.searchUser(userName)
            } catch (ex: Exception) {
                errorStateFlow.value = ex.toString()
            } finally {
                isLoadingStateFlow.value = false
            }
        }
    }

    fun requestNextPage() {
        mainScope.launch {
            try {
                errorStateFlow.value = null
                gitHubUserAction.fetchNextPage()
            } catch (ex: Exception) {
                errorStateFlow.value = ex.toString()
            }
        }
    }
}
