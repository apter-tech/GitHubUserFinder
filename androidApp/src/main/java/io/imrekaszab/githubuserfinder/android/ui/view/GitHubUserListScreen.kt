package io.imrekaszab.githubuserfinder.android.ui.view

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import io.imrekaszab.githubuserfinder.android.ui.navigation.GitHubUserScreens
import io.imrekaszab.githubuserfinder.android.ui.widget.GitHubProjectRow
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

    Scaffold(topBar = { SearchAppBar { viewModel.searchUser(it) } })
    {
        if (isLoading.value) {
            LoadingView()
        } else {
            GitHubUserListView(navController = navController, itemList = itemList.value)
        }
    }
}

@Composable
fun GitHubUserListView(navController: NavController, itemList: List<GitHubUser>) {
    LazyColumn {
        items(items = itemList) {
            GitHubProjectRow(item = it) { userName ->
                navController.navigate(
                    GitHubUserScreens.GitHubUserDetailScreen.route + "/$userName"
                )
            }
        }
    }
}
