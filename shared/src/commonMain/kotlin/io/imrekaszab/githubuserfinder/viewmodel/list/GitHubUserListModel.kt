package io.imrekaszab.githubuserfinder.viewmodel.list

import io.imrekaszab.githubuserfinder.model.domain.GitHubUser
import io.imrekaszab.githubuserfinder.util.reducer.UiEvent
import io.imrekaszab.githubuserfinder.util.reducer.UiState

sealed class UserListScreenUiEvent : UiEvent {
    data class Search(val query: String) : UserListScreenUiEvent()
    object RequestNextPage : UserListScreenUiEvent()
    object LoadUsers : UserListScreenUiEvent()
}

data class UserListScreenState(
    val isLoading: Boolean,
    val data: List<GitHubUser>,
    val isFetchingFinished: Boolean,
    val navigateToDetails: Boolean
) : UiState {
    companion object {
        fun initial() = UserListScreenState(
            isLoading = false,
            data = emptyList(),
            isFetchingFinished = true,
            navigateToDetails = false
        )
    }
}
