package io.imrekaszab.githubuserfinder.android.ui.view

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import io.imrekaszab.githubuserfinder.android.ui.navigation.GitHubUserScreens
import io.imrekaszab.githubuserfinder.android.ui.widget.EmptyView
import io.imrekaszab.githubuserfinder.android.ui.widget.ErrorView
import io.imrekaszab.githubuserfinder.android.ui.widget.GitHubUserRow
import io.imrekaszab.githubuserfinder.android.ui.widget.InfiniteLoadingListView
import io.imrekaszab.githubuserfinder.android.ui.widget.LoadingView
import io.imrekaszab.githubuserfinder.android.ui.widget.SearchAppBar
import io.imrekaszab.githubuserfinder.model.domain.GitHubUser
import io.imrekaszab.githubuserfinder.viewmodel.list.GitHubUserListViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GitHubUserListScreen(navController: NavController) {
    val viewModel = remember { GitHubUserListViewModel() }
    val state = viewModel.state.collectAsState()

    Scaffold(topBar = {
        SearchAppBar(
            onSearchCLick = { viewModel.searchUser(it) },
            onStarClick = {
                navController.navigate(GitHubUserScreens.FavouriteGitHubUsersScreen.route)
            }
        )
    }) {
        when {
            state.value.error.isNotEmpty() -> ErrorView(state.value.error)
            state.value.isLoading -> LoadingView()
            state.value.data.isEmpty() -> EmptyView()
            else -> GitHubUserListView(
                navController = navController,
                itemList = state.value.data,
                showFavouriteIconOnItem = true,
                isFetchingFinished = state.value.isFetchingFinished,
                loadMore = { viewModel.requestNextPage() }
            )
        }
    }
}

@Composable
fun GitHubUserListView(
    navController: NavController,
    itemList: List<GitHubUser>,
    showFavouriteIconOnItem: Boolean = false,
    isFetchingFinished: Boolean = true,
    loadMore: () -> Unit = {}
) {
    InfiniteLoadingListView(
        items = itemList,
        isFetchingFinished = isFetchingFinished,
        loadMore = { loadMore() }
    ) { _, item ->
        GitHubUserRow(item = item as GitHubUser, showFavouriteIconOnItem) { userName ->
            navController.navigate(
                GitHubUserScreens.GitHubUserDetailScreen.route + "/$userName"
            )
        }
    }
}
