package io.imrekaszab.githubuserfinder.viewmodel.list

import io.imrekaszab.githubuserfinder.service.action.GitHubUserAction
import io.imrekaszab.githubuserfinder.service.store.GitHubUserStore
import io.imrekaszab.githubuserfinder.util.reducer.Reducer
import kotlinx.coroutines.flow.first

class GitHubUserListViewModel(
    private val gitHubUserAction: GitHubUserAction,
    private val gitHubUserStore: GitHubUserStore
) : Reducer<UserListScreenState, UserListScreenUiEvent>(UserListScreenState.initial()) {

    fun searchUser(userName: String? = null) {
        sendEvent(UserListScreenUiEvent.Search(query = userName ?: ""))
    }

    fun requestNextPage() {
        sendEvent(UserListScreenUiEvent.RequestNextPage)
    }

    fun loadUsers() {
        sendEvent(UserListScreenUiEvent.LoadUsers)
    }

    override suspend fun reduce(oldState: UserListScreenState, event: UserListScreenUiEvent) {
        when (event) {
            is UserListScreenUiEvent.Search -> {
                setState(oldState.copy(isLoading = true))
                gitHubUserAction.searchUser(event.query)
                refreshStateByUsers(oldState)
            }
            is UserListScreenUiEvent.RequestNextPage -> {
                gitHubUserAction.fetchNextPage()
                refreshStateByUsers(oldState)
            }
            UserListScreenUiEvent.LoadUsers -> {
                refreshStateByUsers(oldState)
            }
        }
    }

    private suspend fun refreshStateByUsers(oldState: UserListScreenState) {
        val users = gitHubUserStore.getUsers().first()
        val isFetchingFinished = gitHubUserStore.isFetchingFinished().first()
        setState(
            oldState.copy(
                isLoading = false,
                data = users,
                isFetchingFinished = isFetchingFinished
            )
        )
    }
}
