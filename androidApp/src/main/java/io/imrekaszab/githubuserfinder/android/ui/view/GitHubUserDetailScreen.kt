package io.imrekaszab.githubuserfinder.android.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import io.imrekaszab.githubuserfinder.android.ui.theme.Dimens
import io.imrekaszab.githubuserfinder.android.ui.theme.GitHubUserFinderTheme
import io.imrekaszab.githubuserfinder.android.ui.widget.CommonAppBar
import io.imrekaszab.githubuserfinder.android.ui.widget.ErrorView
import io.imrekaszab.githubuserfinder.android.ui.widget.FavoriteButton
import io.imrekaszab.githubuserfinder.android.ui.widget.GitHubUserDetailsView
import io.imrekaszab.githubuserfinder.android.ui.widget.LoadingView
import io.imrekaszab.githubuserfinder.viewmodel.details.GitHubUserDetailsViewModel
import io.imrekaszab.githubuserfinder.viewmodel.details.UserDetailsScreenState
import org.koin.compose.koinInject

@Composable
fun GitHubUserDetailScreen(navController: NavController, userName: String?) {
    val viewModel: GitHubUserDetailsViewModel = koinInject()
    LaunchedEffect(userName) {
        userName ?: return@LaunchedEffect
        viewModel.refreshUserDetails(userName)
    }
    val state = viewModel.state.collectAsState()
    val error = viewModel.error.collectAsState()

    GitHubUserDetailContent(
        state = state.value,
        error = error.value,
        onArrowClick = { navController.popBackStack() },
        onFavouriteClick = {
            if (it) {
                viewModel.saveUser()
            } else {
                viewModel.deleteUser()
            }
        }
    )
}

@Composable
fun GitHubUserDetailContent(
    state: UserDetailsScreenState,
    error: String? = null,
    onArrowClick: () -> Unit = {},
    onFavouriteClick: (Boolean) -> Unit = {},
) {
    val userDetails = state.userDetails

    Scaffold(topBar = {
        CommonAppBar(
            title = userDetails?.login ?: "",
            onArrowClick = onArrowClick,
            trailing = {
                FavoriteButton(
                    Modifier.padding(end = Dimens.tiny),
                    isFavourite = state.userDetails?.favourite ?: false,
                    onFavouriteClick = onFavouriteClick
                )
            }
        )
    }) {
        Box(modifier = Modifier.padding(it)) {
            when {
                !error.isNullOrEmpty() -> ErrorView(error)
                userDetails != null ->
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .padding(it)
                    ) {
                        GitHubUserDetailsView(userDetails = userDetails)
                    }
                else -> LoadingView()
            }
        }
    }
}

@Preview(name = "GitHubUserDetailScreen", group = "Screens")
@Composable
private fun GitHubUserDetailScreenPreview() {
    GitHubUserFinderTheme {
        GitHubUserDetailContent(UserDetailsScreenState.initial())
    }
}
