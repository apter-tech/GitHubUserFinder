package io.imrekaszab.githubuserfinder.viewmodel.list

import co.touchlab.kermit.Logger
import io.imrekaszab.githubuserfinder.action.GitHubUserAction
import io.imrekaszab.githubuserfinder.di.injectLogger
import io.imrekaszab.githubuserfinder.store.GitHubUserStore
import io.imrekaszab.githubuserfinder.util.mvi.Reducer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GitHubUserListViewModel :
    Reducer<UserListScreenState, UserListScreenUiEvent>(UserListScreenState.initial()),
    KoinComponent {
    private val logger: Logger by injectLogger("GitHubUserListViewModel")
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
            try {
                when (event) {
                    is UserListScreenUiEvent.RequestNextPage -> {
                        setState(oldState.copy(error = ""))
                        gitHubUserAction.fetchNextPage()
                    }
                    is UserListScreenUiEvent.ShowData -> {
                        setState(oldState.copy(error = "", isLoading = false, data = event.items))
                    }
                    is UserListScreenUiEvent.Search -> {
                        setState(oldState.copy(error = "", isLoading = true))
                        gitHubUserAction.searchUser(event.query)
                    }
                    is UserListScreenUiEvent.FetchingFinished -> {
                        setState(oldState.copy(isFetchingFinished = event.finished))
                    }
                    is UserListScreenUiEvent.NavigateToDetails -> {
                        gitHubUserAction.refreshUserDetails(event.userName)
                        setState(oldState.copy(navigateToDetails = true))
                    }
                }
            } catch (exception: Exception) {
                logger.e("Error happened: $exception")
                setState(oldState.copy(error = exception.toString(), isLoading = false))
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
