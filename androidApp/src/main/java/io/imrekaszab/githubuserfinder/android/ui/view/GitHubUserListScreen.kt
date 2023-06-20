package io.imrekaszab.githubuserfinder.android.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import io.imrekaszab.githubuserfinder.android.ui.navigation.GitHubUserScreens
import io.imrekaszab.githubuserfinder.android.ui.theme.GitHubUserFinderTheme
import io.imrekaszab.githubuserfinder.android.ui.widget.EmptyView
import io.imrekaszab.githubuserfinder.android.ui.widget.ErrorView
import io.imrekaszab.githubuserfinder.android.ui.widget.GitHubUserListView
import io.imrekaszab.githubuserfinder.android.ui.widget.LoadingView
import io.imrekaszab.githubuserfinder.android.ui.widget.SearchAppBar
import io.imrekaszab.githubuserfinder.viewmodel.list.GitHubUserListViewModel
import io.imrekaszab.githubuserfinder.viewmodel.list.UserListScreenState
import org.koin.compose.koinInject

@Composable
fun GitHubUserListScreen(navController: NavController) {
    val viewModel: GitHubUserListViewModel = koinInject()
    val state = viewModel.state.collectAsState()
    val error = viewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadUsers()
    }

    GitHubUserListContent(
        state = state.value,
        error = error.value,
        onSearchCLick = { viewModel.searchUser(it) },
        onStarClick = { navController.navigate(GitHubUserScreens.FavouriteGitHubUsersScreen.route) },
        loadMore = { viewModel.requestNextPage() },
        onItemClick = {
            navController.navigate(GitHubUserScreens.GitHubUserDetailScreen.route + "/$it")
        }
    )
}

@Composable
fun GitHubUserListContent(
    state: UserListScreenState,
    error: String? = null,
    onSearchCLick: (String) -> Unit = {},
    onStarClick: () -> Unit = {},
    loadMore: () -> Unit = {},
    onItemClick: (String) -> Unit = {},
) {
    Scaffold(topBar = {
        SearchAppBar(onSearchCLick = onSearchCLick, onStarClick = onStarClick)
    }) {
        Box(modifier = Modifier.padding(it)) {
            when {
                !error.isNullOrEmpty() -> ErrorView(error)
                state.isLoading -> LoadingView()
                state.data.isEmpty() -> EmptyView()
                else -> GitHubUserListView(
                    itemList = state.data,
                    showFavouriteIconOnItem = true,
                    isFetchingFinished = state.isFetchingFinished,
                    loadMore = loadMore,
                    onItemClick = onItemClick
                )
            }
        }
    }
}

@Preview(name = "GitHubUserListScreen", group = "Screens")
@Composable
private fun GitHubUserListScreenPreview() {
    GitHubUserFinderTheme {
        GitHubUserListContent(UserListScreenState.initial())
    }
}
