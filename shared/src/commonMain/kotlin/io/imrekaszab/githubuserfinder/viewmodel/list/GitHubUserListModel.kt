package io.imrekaszab.githubuserfinder.viewmodel.list

import io.imrekaszab.githubuserfinder.model.domain.GitHubUser
import io.imrekaszab.githubuserfinder.util.mvi.UiEvent
import io.imrekaszab.githubuserfinder.util.mvi.UiState

sealed class UserListScreenUiEvent : UiEvent {
    data class ShowData(val items: List<GitHubUser>) : UserListScreenUiEvent()
    data class FetchingFinished(val finished: Boolean) : UserListScreenUiEvent()
    data class Search(val query: String) : UserListScreenUiEvent()
    object RequestNextPage : UserListScreenUiEvent()
}

data class UserListScreenState(
    val isLoading: Boolean,
    val data: List<GitHubUser>,
    val isFetchingFinished: Boolean,
    val error: String
) : UiState {
    companion object {
        fun initial() = UserListScreenState(
            isLoading = false,
            data = emptyList(),
            isFetchingFinished = true,
            error = ""
        )
    }

    override fun toString(): String {
        return "isLoading: $isLoading, data.size: ${data.size}, isFetchingFinished: $isFetchingFinished"
    }
}
