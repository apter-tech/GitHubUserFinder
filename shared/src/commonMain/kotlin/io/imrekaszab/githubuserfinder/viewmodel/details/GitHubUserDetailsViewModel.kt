package io.imrekaszab.githubuserfinder.viewmodel.details

import io.imrekaszab.githubuserfinder.action.GitHubUserAction
import io.imrekaszab.githubuserfinder.store.GitHubUserStore
import io.imrekaszab.githubuserfinder.util.mvi.Reducer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GitHubUserDetailsViewModel :
    Reducer<UserDetailsScreenState, UserDetailsScreenUiEvent>(UserDetailsScreenState.initial()),
    KoinComponent {
    private val gitHubUserAction: GitHubUserAction by inject()
    private val gitHubUserStore: GitHubUserStore by inject()

    init {
        mainScope.launch {
            gitHubUserStore.getUserDetails().collectLatest {
                sendEvent(UserDetailsScreenUiEvent.ShowData(it))
            }
        }
    }

    fun refreshUserDetails(userName: String) {
        sendEvent(UserDetailsScreenUiEvent.RefreshUser(userName))
    }

    fun saveUser() {
        sendEvent(UserDetailsScreenUiEvent.SaveUser)
    }

    fun deleteUser() {
        sendEvent(UserDetailsScreenUiEvent.DeleteUser)
    }

    override fun reduce(oldState: UserDetailsScreenState, event: UserDetailsScreenUiEvent) {
        mainScope.launch {
            try {
                when (event) {
                    UserDetailsScreenUiEvent.DeleteUser -> {
                        setState(oldState.copy(error = ""))
                        gitHubUserAction.deleteUser()
                    }
                    is UserDetailsScreenUiEvent.RefreshUser -> {
                        setState(oldState.copy(error = ""))
                        gitHubUserAction.refreshUserDetails(event.userName)
                    }
                    UserDetailsScreenUiEvent.SaveUser -> {
                        setState(oldState.copy(error = ""))
                        gitHubUserAction.saveUser()
                    }
                    is UserDetailsScreenUiEvent.ShowData -> {
                        setState(oldState.copy(userDetails = event.userDetails))
                    }
                }
            } catch (ex: Exception) {
                setState(oldState.copy(error = ex.message ?: ""))
            }
        }
    }
}
