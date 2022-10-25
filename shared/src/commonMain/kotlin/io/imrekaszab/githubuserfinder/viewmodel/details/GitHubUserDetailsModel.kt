package io.imrekaszab.githubuserfinder.viewmodel.details

import io.imrekaszab.githubuserfinder.model.domain.GitHubUser
import io.imrekaszab.githubuserfinder.util.mvi.UiEvent
import io.imrekaszab.githubuserfinder.util.mvi.UiState

sealed class UserDetailsScreenUiEvent : UiEvent {
    data class RefreshUser(val userName: String) : UserDetailsScreenUiEvent()
    data class ShowData(val userDetails: GitHubUser?) : UserDetailsScreenUiEvent()
    object DeleteUser : UserDetailsScreenUiEvent()
    object SaveUser : UserDetailsScreenUiEvent()
}

data class UserDetailsScreenState(
    val userDetails: GitHubUser?,
    val error: String
) : UiState {
    companion object {
        fun initial() = UserDetailsScreenState(
            userDetails = null,
            error = ""
        )
    }

    override fun toString() = "data.size: $userDetails"
}
