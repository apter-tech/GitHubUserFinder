package io.imrekaszab.githubuserfinder.viewmodel.details

import io.imrekaszab.githubuserfinder.service.action.GitHubUserAction
import io.imrekaszab.githubuserfinder.service.store.GitHubUserStore
import io.imrekaszab.githubuserfinder.util.reducer.Reducer
import kotlinx.coroutines.flow.first

class GitHubUserDetailsViewModel(
    private val gitHubUserAction: GitHubUserAction,
    private val gitHubUserStore: GitHubUserStore
) : Reducer<UserDetailsScreenState, UserDetailsScreenUiEvent>(UserDetailsScreenState.initial()) {

    fun refreshUserDetails(userName: String) {
        sendEvent(UserDetailsScreenUiEvent.RefreshUser(userName))
    }

    fun saveUser() {
        sendEvent(UserDetailsScreenUiEvent.SaveUser)
    }

    fun deleteUser() {
        sendEvent(UserDetailsScreenUiEvent.DeleteUser)
    }

    override suspend fun reduce(oldState: UserDetailsScreenState, event: UserDetailsScreenUiEvent) {
        when (event) {
            UserDetailsScreenUiEvent.DeleteUser -> {
                gitHubUserAction.deleteUser()
                updateUserDetails(oldState)
            }
            is UserDetailsScreenUiEvent.RefreshUser -> {
                gitHubUserAction.refreshUserDetails(event.userName)
                updateUserDetails(oldState)
            }
            UserDetailsScreenUiEvent.SaveUser -> {
                gitHubUserAction.saveUser()
                updateUserDetails(oldState)
            }
        }
    }

    private suspend fun updateUserDetails(oldState: UserDetailsScreenState) {
        val userDetails = gitHubUserStore.getUserDetails().first()
        setState(oldState.copy(userDetails = userDetails))
    }
}
