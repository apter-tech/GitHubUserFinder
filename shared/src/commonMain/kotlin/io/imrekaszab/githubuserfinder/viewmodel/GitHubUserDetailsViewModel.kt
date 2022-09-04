package io.imrekaszab.githubuserfinder.viewmodel

import io.imrekaszab.githubuserfinder.action.GitHubUserAction
import io.imrekaszab.githubuserfinder.model.domain.GitHubUser
import io.imrekaszab.githubuserfinder.store.GitHubUserStore
import io.imrekaszab.githubuserfinder.util.CommonViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GitHubUserDetailsViewModel : CommonViewModel(), KoinComponent {
    private val gitHubUserAction: GitHubUserAction by inject()
    private val gitHubUserStore: GitHubUserStore by inject()
    val userDetails: Flow<GitHubUser> = gitHubUserStore.getUserDetails()
        .flowOn(Dispatchers.Main)

    fun refreshUserDetails(userName: String) {
        mainScope.launch {
            try {
                errorStateFlow.value = null
                gitHubUserAction.refreshUserDetails(userName)
            } catch (ex: Exception) {
                errorStateFlow.value = ex.toString()
            }
        }
    }
}
