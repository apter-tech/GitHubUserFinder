package io.imrekaszab.githubuserfinder.android.ui.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import io.imrekaszab.githubuserfinder.android.R
import io.imrekaszab.githubuserfinder.android.ui.theme.Dimens
import io.imrekaszab.githubuserfinder.model.domain.GitHubUser

@Composable
fun GitHubUserRow(
    item: GitHubUser,
    showFavouriteIconOnItem: Boolean,
    onItemCLick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(Dimens.tiny)
            .fillMaxWidth()
            .height(Dimens.cardHeight)
            .clickable {
                onItemCLick(item.login)
            },
        shape = MaterialTheme.shapes.large,
        elevation = Dimens.tiny
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.default),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(Dimens.imageSize),
                shape = MaterialTheme.shapes.large,
                color = MaterialTheme.colors.secondaryVariant.copy(alpha = 0.2f)
            ) {
                AsyncImage(
                    model = item.avatarUrl,
                    modifier = Modifier.size(Dimens.imageSize),
                    contentDescription = item.login
                )
            }
            Text(
                item.login,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(Dimens.tiny)
            )
            Spacer(modifier = Modifier.weight(1.0f))
            if (showFavouriteIconOnItem && item.favourite) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = stringResource(id = R.string.favourite_button_content_description)
                )
            }
        }
    }
}
