package io.imrekaszab.githubuserfinder.viewmodel.favourite

import io.imrekaszab.githubuserfinder.model.domain.GitHubUser
import io.imrekaszab.githubuserfinder.util.reducer.UiEvent
import io.imrekaszab.githubuserfinder.util.reducer.UiState

sealed class FavouriteUsersScreenUiEvent : UiEvent {
    object DeleteUsers : FavouriteUsersScreenUiEvent()
    object LoadUsers : FavouriteUsersScreenUiEvent()
}

data class FavouriteUsersScreenState(val data: List<GitHubUser>) : UiState {
    companion object {
        fun initial() = FavouriteUsersScreenState(data = emptyList())
    }
}
