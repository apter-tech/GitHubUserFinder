package io.imrekaszab.githubuserfinder.viewmodel.favourite

import io.imrekaszab.githubuserfinder.action.GitHubUserAction
import io.imrekaszab.githubuserfinder.store.GitHubUserStore
import io.imrekaszab.githubuserfinder.util.mvi.Reducer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FavouriteUsersViewModel :
    Reducer<FavouriteUsersScreenState, FavouriteUsersScreenUiEvent>(FavouriteUsersScreenState.initial()),
    KoinComponent {
    private val gitHubUserAction: GitHubUserAction by inject()
    private val gitHubUserStore: GitHubUserStore by inject()

    init {
        mainScope.launch {
            gitHubUserStore.getSavedUsers()
                .collectLatest {
                    sendEvent(FavouriteUsersScreenUiEvent.ShowData(it))
                }
        }
    }

    fun deleteAllUser() {
        sendEvent(FavouriteUsersScreenUiEvent.DeleteUsers)
    }

    override fun reduce(oldState: FavouriteUsersScreenState, event: FavouriteUsersScreenUiEvent) {
        mainScope.launch {
            when (event) {
                FavouriteUsersScreenUiEvent.DeleteUsers -> {
                    gitHubUserAction.deleteAllUser()
                }
                is FavouriteUsersScreenUiEvent.ShowData -> {
                    setState(oldState.copy(data = event.items))
                }
            }
        }
    }
}
