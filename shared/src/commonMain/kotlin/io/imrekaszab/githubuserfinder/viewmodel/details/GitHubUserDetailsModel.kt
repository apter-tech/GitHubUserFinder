package io.imrekaszab.githubuserfinder.viewmodel.details

import io.imrekaszab.githubuserfinder.model.domain.GitHubUser
import io.imrekaszab.githubuserfinder.util.reducer.UiEvent
import io.imrekaszab.githubuserfinder.util.reducer.UiState

sealed class UserDetailsScreenUiEvent : UiEvent {
    data class RefreshUser(val userName: String) : UserDetailsScreenUiEvent()
    object DeleteUser : UserDetailsScreenUiEvent()
    object SaveUser : UserDetailsScreenUiEvent()
}

data class UserDetailsScreenState(val userDetails: GitHubUser?) : UiState {
    companion object {
        fun initial() = UserDetailsScreenState(userDetails = null)
    }
}
