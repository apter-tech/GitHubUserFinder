package io.imrekaszab.githubuserfinder.android.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import coil.compose.AsyncImage
import io.imrekaszab.githubuserfinder.android.ui.theme.Dimens
import io.imrekaszab.githubuserfinder.android.ui.widget.ErrorView
import io.imrekaszab.githubuserfinder.android.ui.widget.GitHubUserDetailItemView
import io.imrekaszab.githubuserfinder.android.ui.widget.LoadingView
import io.imrekaszab.githubuserfinder.android.viewmodel.GitHubUserDetailsViewModel
import io.imrekaszab.githubuserfinder.model.domain.GitHubUserDetails
import org.koin.androidx.compose.getViewModel

@Composable
fun GitHubUserDetailScreen(navController: NavController, userName: String?) {
    val viewModel = getViewModel<GitHubUserDetailsViewModel>()
    val userDetails by viewModel.userDetails.collectAsState(initial = null)
    val errorHappened by viewModel.errorStateFlow.collectAsState(initial = null)

    LaunchedEffect(Unit) {
        userName ?: return@LaunchedEffect
        viewModel.refreshUserDetails(userName)
    }
    Scaffold(topBar = {
        TopAppBar {
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.padding(start = Dimens.tiny)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Arrow Back",
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    }
                )
                Spacer(modifier = Modifier.width(Dimens.default))
                Text(text = userDetails?.login ?: "", style = MaterialTheme.typography.h6)
            }
        }
    }) {
        when {
            !errorHappened.isNullOrEmpty() -> ErrorView(errorHappened)
            userDetails != null ->
                Column(modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(it)) {
                    GitHubUserDetailsView(userDetails = userDetails)
                }
            else -> LoadingView()
        }
    }
}

@Composable
fun GitHubUserDetailsView(userDetails: GitHubUserDetails?) {
    userDetails ?: return
    Card(
        modifier = Modifier
            .padding(Dimens.default)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        elevation = Dimens.tiny
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.default),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                userDetails.name,
                style = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(Dimens.default))
            Surface(
                modifier = Modifier.size(Dimens.bigImageSize),
                shape = MaterialTheme.shapes.large,
                color = MaterialTheme.colors.secondaryVariant.copy(alpha = 0.2f)
            ) {
                AsyncImage(
                    model = userDetails.avatarUrl,
                    modifier = Modifier.size(Dimens.bigImageSize),
                    contentDescription = userDetails.name
                )
            }
            Spacer(modifier = Modifier.height(Dimens.default))
            Text(
                userDetails.bio ?: "",
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(Dimens.default))
            GitHubUserDetailItemView(label = "Followers", value = userDetails.followers.toString())
            GitHubUserDetailItemView(label = "Following", value = userDetails.following.toString())
            GitHubUserDetailItemView(
                label = "Public repos",
                value = userDetails.publicRepos.toString()
            )
            GitHubUserDetailItemView(label = "Company", value = userDetails.company)
            GitHubUserDetailItemView(label = "Location", value = userDetails.location)
            GitHubUserDetailItemView(label = "Email", value = userDetails.email)
            GitHubUserDetailItemView(label = "Blog", value = userDetails.blog)
            GitHubUserDetailItemView(label = "Twitter", value = userDetails.twitterUsername)
        }
    }
}
