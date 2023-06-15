package io.imrekaszab.githubuserfinder.android.ui.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import coil.compose.AsyncImage
import io.imrekaszab.githubuserfinder.android.R
import io.imrekaszab.githubuserfinder.android.ui.theme.Dimens
import io.imrekaszab.githubuserfinder.model.domain.GitHubUser

@Composable
fun GitHubUserDetailsView(userDetails: GitHubUser) {
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
                text = userDetails.bio ?: "",
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(Dimens.default))
            GitHubUserDetailItemViews(userDetails)
        }
    }
}

@Composable
fun GitHubUserDetailItemViews(userDetails: GitHubUser) {
    GitHubUserDetailItemView(
        label = stringResource(id = R.string.details_view_followers),
        value = userDetails.followers.toString()
    )
    GitHubUserDetailItemView(
        label = stringResource(id = R.string.details_view_following),
        value = userDetails.following.toString()
    )
    GitHubUserDetailItemView(
        label = stringResource(id = R.string.details_view_public_repos),
        value = userDetails.publicRepos.toString()
    )
    GitHubUserDetailItemView(
        label = stringResource(id = R.string.details_view_company),
        value = userDetails.company
    )
    GitHubUserDetailItemView(
        label = stringResource(id = R.string.details_view_location),
        value = userDetails.location
    )
    GitHubUserDetailItemView(
        label = stringResource(id = R.string.details_view_email),
        value = userDetails.email
    )
    GitHubUserDetailItemView(
        label = stringResource(id = R.string.details_view_blog),
        value = userDetails.blog
    )
    GitHubUserDetailItemView(
        label = stringResource(id = R.string.details_view_twitter),
        value = userDetails.twitterUsername
    )
}
