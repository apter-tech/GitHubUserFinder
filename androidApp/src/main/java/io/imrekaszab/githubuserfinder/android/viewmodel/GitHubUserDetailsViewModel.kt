package io.imrekaszab.githubuserfinder.android.viewmodel

import androidx.lifecycle.ViewModel
import io.imrekaszab.githubuserfinder.action.GitHubUserAction
import io.imrekaszab.githubuserfinder.model.domain.GitHubUserDetails
import io.imrekaszab.githubuserfinder.store.GitHubUserStore
import kotlinx.coroutines.flow.Flow


class GitHubUserDetailsViewModel(
    private val gitHubUserAction: GitHubUserAction,
    gitHubUserStore: GitHubUserStore
) : ViewModel() {
    val userDetails: Flow<GitHubUserDetails> =
        gitHubUserStore.getUserDetails()

    suspend fun refreshUserDetails(userName: String) {
        gitHubUserAction.refreshUserDetails(userName)
    }
}
