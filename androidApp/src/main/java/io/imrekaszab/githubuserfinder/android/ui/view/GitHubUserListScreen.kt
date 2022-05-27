package io.imrekaszab.githubuserfinder.android.ui.view

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import io.imrekaszab.githubuserfinder.android.ui.navigation.GitHubUserScreens
import io.imrekaszab.githubuserfinder.android.ui.widget.EmptyView
import io.imrekaszab.githubuserfinder.android.ui.widget.GitHubUserRow
import io.imrekaszab.githubuserfinder.android.ui.widget.InfiniteLoadingListView
import io.imrekaszab.githubuserfinder.android.ui.widget.LoadingView
import io.imrekaszab.githubuserfinder.android.ui.widget.SearchAppBar
import io.imrekaszab.githubuserfinder.android.viewmodel.GitHubUserListViewModel
import io.imrekaszab.githubuserfinder.model.domain.GitHubUser
import org.koin.androidx.compose.getViewModel

@Composable
fun GitHubUserListScreen(navController: NavController) {
    val viewModel = getViewModel<GitHubUserListViewModel>()
    val isLoading = viewModel.isLoading.collectAsState(initial = false)
    val itemList = viewModel.users.collectAsState(initial = emptyList())
    val isFetchingFinished = viewModel.isFetchingFinished.collectAsState(initial = false)

    Scaffold(topBar = { SearchAppBar { viewModel.searchUser(it) } }) {
        when {
            isLoading.value -> LoadingView()
            itemList.value.isEmpty() -> EmptyView()
            else -> GitHubUserListView(
                navController = navController,
                itemList = itemList.value,
                isFetchingFinished = isFetchingFinished.value,
                loadMore = { viewModel.requestNextPage() }
            )
        }
    }
}

@Composable
fun GitHubUserListView(
    navController: NavController,
    itemList: List<GitHubUser>,
    isFetchingFinished: Boolean,
    loadMore: () -> Unit
) {
    InfiniteLoadingListView(
        items = itemList,
        isFetchingFinished = isFetchingFinished,
        loadMore = { loadMore() },
    ) { _, item ->
        GitHubUserRow(item = item as GitHubUser) { userName ->
            navController.navigate(
                GitHubUserScreens.GitHubUserDetailScreen.route + "/$userName"
            )
        }
    }
}
