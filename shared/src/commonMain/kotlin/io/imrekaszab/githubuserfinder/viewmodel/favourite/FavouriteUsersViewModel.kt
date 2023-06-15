package io.imrekaszab.githubuserfinder.viewmodel.favourite

import io.imrekaszab.githubuserfinder.service.action.GitHubUserAction
import io.imrekaszab.githubuserfinder.service.store.GitHubUserStore
import io.imrekaszab.githubuserfinder.util.reducer.Reducer
import kotlinx.coroutines.flow.first

class FavouriteUsersViewModel(
    private val gitHubUserAction: GitHubUserAction,
    private val gitHubUserStore: GitHubUserStore
) : Reducer<FavouriteUsersScreenState, FavouriteUsersScreenUiEvent>(
    FavouriteUsersScreenState.initial()
) {
    fun loadUsers() {
        sendEvent(FavouriteUsersScreenUiEvent.LoadUsers)
    }

    fun deleteAllUser() {
        sendEvent(FavouriteUsersScreenUiEvent.DeleteUsers)
    }

    override suspend fun reduce(
        oldState: FavouriteUsersScreenState,
        event: FavouriteUsersScreenUiEvent
    ) {
        when (event) {
            FavouriteUsersScreenUiEvent.DeleteUsers -> {
                gitHubUserAction.deleteAllUsers()
                loadUsers(oldState)
            }
            is FavouriteUsersScreenUiEvent.LoadUsers -> loadUsers(oldState)
        }
    }

    private suspend fun loadUsers(oldState: FavouriteUsersScreenState) {
        val users = gitHubUserStore.getSavedUsers().first()
        setState(oldState.copy(data = users))
    }
}
