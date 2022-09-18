package io.imrekaszab.githubuserfinder.viewmodel.favourite

import io.imrekaszab.githubuserfinder.model.domain.GitHubUser
import io.imrekaszab.githubuserfinder.util.mvi.UiEvent
import io.imrekaszab.githubuserfinder.util.mvi.UiState

sealed class FavouriteUsersScreenUiEvent : UiEvent {
    data class ShowData(val items: List<GitHubUser>) : FavouriteUsersScreenUiEvent()
    object DeleteUsers : FavouriteUsersScreenUiEvent()
}

data class FavouriteUsersScreenState(
    val data: List<GitHubUser>,
    val error: String
) : UiState {
    companion object {
        fun initial() = FavouriteUsersScreenState(
            data = emptyList(),
            error = ""
        )
    }

    override fun toString() = "data.size: ${data.size}"
}
