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
import coil.compose.AsyncImage
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
