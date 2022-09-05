package io.imrekaszab.githubuserfinder.viewmodel

import io.imrekaszab.githubuserfinder.action.GitHubUserAction
import io.imrekaszab.githubuserfinder.store.GitHubUserStore
import io.imrekaszab.githubuserfinder.util.CommonViewModel
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FavouriteUsersViewModel : CommonViewModel(), KoinComponent {
    private val gitHubUserAction: GitHubUserAction by inject()
    private val gitHubUserStore: GitHubUserStore by inject()

    val users = gitHubUserStore.getSavedUsers()

    fun deleteAllUser() {
        mainScope.launch {
            gitHubUserAction.deleteAllUser()
        }
    }
}
