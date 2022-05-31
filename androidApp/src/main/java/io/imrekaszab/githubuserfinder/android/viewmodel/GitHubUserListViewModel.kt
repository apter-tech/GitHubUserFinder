package io.imrekaszab.githubuserfinder.android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.imrekaszab.githubuserfinder.action.GitHubUserAction
import io.imrekaszab.githubuserfinder.store.GitHubUserStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class GitHubUserListViewModel(
    private val gitHubUserAction: GitHubUserAction,
    gitHubUserStore: GitHubUserStore
) : ViewModel() {
    private val isLoadingStateFlow = MutableStateFlow(false)
    val isLoading: Flow<Boolean> = isLoadingStateFlow
    val isFetchingFinished: Flow<Boolean> = gitHubUserStore.isFetchingFinished()
    val users = gitHubUserStore.getUsers()
    val errorStateFlow = MutableStateFlow<String?>(null)

    fun searchUser(userName: String? = null) {
        userName ?: return
        viewModelScope.launch {
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
        viewModelScope.launch {
            try {
                errorStateFlow.value = null
                gitHubUserAction.fetchNextPage()
            } catch (ex: Exception) {
                errorStateFlow.value = ex.toString()
            }
        }
    }
}
