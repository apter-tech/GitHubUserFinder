package io.imrekaszab.githubuserfinder.android.viewmodel

import androidx.lifecycle.ViewModel
import io.imrekaszab.githubuserfinder.action.GitHubUserAction
import io.imrekaszab.githubuserfinder.model.domain.GitHubUserDetails
import io.imrekaszab.githubuserfinder.store.GitHubUserStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class GitHubUserDetailsViewModel(
    private val gitHubUserAction: GitHubUserAction,
    gitHubUserStore: GitHubUserStore
) : ViewModel() {
    val errorStateFlow = MutableStateFlow<String?>(null)
    val userDetails: Flow<GitHubUserDetails> = gitHubUserStore.getUserDetails()

    suspend fun refreshUserDetails(userName: String) {
        try {
            errorStateFlow.value = null
            gitHubUserAction.refreshUserDetails(userName)
        } catch (ex: Exception) {
            errorStateFlow.value = ex.toString()
        }
    }
}
