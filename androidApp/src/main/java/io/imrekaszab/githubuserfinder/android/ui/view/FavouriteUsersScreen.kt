package io.imrekaszab.githubuserfinder.android.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import io.imrekaszab.githubuserfinder.android.R
import io.imrekaszab.githubuserfinder.android.ui.navigation.GitHubUserScreens
import io.imrekaszab.githubuserfinder.android.ui.theme.Dimens
import io.imrekaszab.githubuserfinder.android.ui.theme.GitHubUserFinderTheme
import io.imrekaszab.githubuserfinder.android.ui.widget.CommonAppBar
import io.imrekaszab.githubuserfinder.android.ui.widget.EmptyView
import io.imrekaszab.githubuserfinder.android.ui.widget.ErrorView
import io.imrekaszab.githubuserfinder.android.ui.widget.GitHubUserListView
import io.imrekaszab.githubuserfinder.android.ui.widget.RemoveAllUserDialog
import io.imrekaszab.githubuserfinder.viewmodel.favourite.FavouriteUsersScreenState
import io.imrekaszab.githubuserfinder.viewmodel.favourite.FavouriteUsersViewModel
import org.koin.compose.koinInject

@Composable
fun FavouriteGitHubUsersScreen(navController: NavController) {
    val viewModel: FavouriteUsersViewModel = koinInject()
    val state = viewModel.state.collectAsState()
    val error = viewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadUsers()
    }

    FavouriteGitHubUsersContent(
        state = state.value,
        error = error.value,
        onArrowClick = { navController.popBackStack() },
        onRemoveUsers = { viewModel.deleteAllUser() },
        onItemClick = {
            navController.navigate(GitHubUserScreens.GitHubUserDetailScreen.route + "/$it")
        }
    )
}

@Composable
fun FavouriteGitHubUsersContent(
    state: FavouriteUsersScreenState,
    error: String? = null,
    onArrowClick: () -> Unit = {},
    onRemoveUsers: () -> Unit = {},
    onItemClick: (String) -> Unit = {},
) {
    val showRemoveDialog = remember { mutableStateOf(false) }
    Scaffold(topBar = {
        CommonAppBar(
            title = stringResource(id = R.string.favourite_screen_title),
            onArrowClick = onArrowClick,
            trailing = {
                if (state.data.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = stringResource(id = R.string.clear_button_content_description),
                        modifier = Modifier
                            .clickable { showRemoveDialog.value = true }
                            .padding(end = Dimens.tiny)
                    )
                }
            }
        )
    }) {
        Box(modifier = Modifier.padding(it)) {
            when {
                !error.isNullOrEmpty() -> ErrorView(error)
                state.data.isEmpty() -> EmptyView()
                else -> GitHubUserListView(
                    itemList = state.data,
                    onItemClick = onItemClick
                )
            }
            if (showRemoveDialog.value) {
                RemoveAllUserDialog(
                    onPositiveButtonClick = {
                        onRemoveUsers()
                        showRemoveDialog.value = false
                    },
                    onDismissRequest = {
                        showRemoveDialog.value = false
                    }
                )
            }
        }
    }
}

@Preview(name = "FavouriteGitHubUsersScreen", group = "Screens")
@Composable
private fun FavouriteGitHubUsersScreenPreview() {
    GitHubUserFinderTheme {
        FavouriteGitHubUsersContent(FavouriteUsersScreenState.initial())
    }
}
