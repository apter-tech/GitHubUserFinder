package io.imrekaszab.githubuserfinder.viewmodel.list

import io.imrekaszab.githubuserfinder.action.GitHubUserAction
import io.imrekaszab.githubuserfinder.store.GitHubUserStore
import io.imrekaszab.githubuserfinder.util.mvi.Reducer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GitHubUserListViewModel :
    Reducer<UserListScreenState, UserListScreenUiEvent>(UserListScreenState.initial()),
    KoinComponent {
    private val gitHubUserAction: GitHubUserAction by inject()
    private val gitHubUserStore: GitHubUserStore by inject()

    init {
        mainScope.launch {
            gitHubUserStore.getUsers()
                .collectLatest {
                    sendEvent(UserListScreenUiEvent.ShowData(it))
                }

            gitHubUserStore.isFetchingFinished()
                .collectLatest {
                    sendEvent(UserListScreenUiEvent.FetchingFinished(it))
                }
        }
    }

    override fun reduce(oldState: UserListScreenState, event: UserListScreenUiEvent) {
        mainScope.launch {
            when (event) {
                is UserListScreenUiEvent.RequestNextPage -> {
                    try {
                        setState(oldState.copy(error = ""))
                        gitHubUserAction.fetchNextPage()
                    } catch (ex: Exception) {
                        setState(oldState.copy(error = ex.toString(), isLoading = false))
                    }
                }
                is UserListScreenUiEvent.ShowData -> {
                    setState(oldState.copy(error = "", isLoading = false, data = event.items))
                }
                is UserListScreenUiEvent.Search -> {
                    try {
                        setState(oldState.copy(error = "", isLoading = true))
                        gitHubUserAction.searchUser(event.query)
                    } catch (ex: Exception) {
                        setState(oldState.copy(error = ex.toString(), isLoading = false))
                    }
                }
                is UserListScreenUiEvent.FetchingFinished -> {
                    setState(oldState.copy(isFetchingFinished = event.finished))
                }
                is UserListScreenUiEvent.NavigateToDetails -> {
                    try {
                        gitHubUserAction.refreshUserDetails(event.userName)
                        setState(oldState.copy(navigateToDetails = true))
                    } catch (ex: Exception) {
                        setState(oldState.copy(error = ex.toString(), isLoading = false))
                    }
                }
            }
        }
    }

    fun searchUser(userName: String? = null) {
        sendEvent(UserListScreenUiEvent.Search(query = userName ?: ""))
    }

    fun requestNextPage() {
        sendEvent(UserListScreenUiEvent.RequestNextPage)
    }

    fun navigateToDetails(userName: String) {
        sendEvent(UserListScreenUiEvent.NavigateToDetails(userName))
    }
}
