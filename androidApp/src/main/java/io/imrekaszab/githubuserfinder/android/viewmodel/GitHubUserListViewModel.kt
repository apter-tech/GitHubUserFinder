package io.imrekaszab.githubuserfinder.android.viewmodel

import android.util.Log
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
    private val isFetchingFinishedStateFlow = MutableStateFlow(false)
    val isLoading: Flow<Boolean> = isLoadingStateFlow
    val isFetchingFinished: Flow<Boolean> = isFetchingFinishedStateFlow
    val users = gitHubUserStore.getUsers()

    init {
        gitHubUserStore.isFetchingFinished()
            .subscribe {
                isFetchingFinishedStateFlow.value = it
            }
    }

    fun searchUser(userName: String? = null) {
        userName ?: return
        viewModelScope.launch {
            try {
                isLoadingStateFlow.value = true
                gitHubUserAction.searchUser(userName)
            } catch (ex: Exception) {
                // TODO handle error
                // TODO create error view
                Log.d("Error", ex.toString())
            } finally {
                isLoadingStateFlow.value = false
            }
        }
    }

    fun requestNextPage() {
        viewModelScope.launch {
            try {
                gitHubUserAction.fetchNextPage()
            } catch (ex: Exception) {
                // TODO handle error
                // TODO create error view
                Log.d("Error", ex.toString())
            }
        }
    }
}
