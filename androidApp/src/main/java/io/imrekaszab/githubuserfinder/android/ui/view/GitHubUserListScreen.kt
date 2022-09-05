package io.imrekaszab.githubuserfinder.android.ui.view

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import io.imrekaszab.githubuserfinder.android.ui.navigation.GitHubUserScreens
import io.imrekaszab.githubuserfinder.android.ui.widget.EmptyView
import io.imrekaszab.githubuserfinder.android.ui.widget.ErrorView
import io.imrekaszab.githubuserfinder.android.ui.widget.GitHubUserRow
import io.imrekaszab.githubuserfinder.android.ui.widget.InfiniteLoadingListView
import io.imrekaszab.githubuserfinder.android.ui.widget.LoadingView
import io.imrekaszab.githubuserfinder.android.ui.widget.SearchAppBar
import io.imrekaszab.githubuserfinder.model.domain.GitHubUser
import io.imrekaszab.githubuserfinder.viewmodel.GitHubUserListViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GitHubUserListScreen(navController: NavController) {
    val viewModel = GitHubUserListViewModel()
    val isLoading by viewModel.isLoading.collectAsState(initial = false)
    val errorHappened by viewModel.error.collectAsState(initial = null)
    val itemList by viewModel.users.collectAsState(initial = emptyList())
    val isFetchingFinished by viewModel.isFetchingFinished.collectAsState(initial = false)

    Scaffold(topBar = {
        SearchAppBar(
            onSearchCLick = { viewModel.searchUser(it) },
            onStarClick = {
                navController.navigate(GitHubUserScreens.FavouriteGitHubUsersScreen.route)
            }
        )
    }) {
        when {
            !errorHappened.isNullOrEmpty() -> ErrorView(errorHappened)
            isLoading -> LoadingView()
            itemList.isEmpty() -> EmptyView()
            else -> GitHubUserListView(
                navController = navController,
                itemList = itemList,
                showFavouriteIconOnItem = true,
                isFetchingFinished = isFetchingFinished,
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
