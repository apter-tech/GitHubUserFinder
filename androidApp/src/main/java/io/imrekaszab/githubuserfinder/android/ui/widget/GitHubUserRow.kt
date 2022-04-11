package io.imrekaszab.githubuserfinder.android.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import io.imrekaszab.githubuserfinder.android.ui.theme.Dimens
import io.imrekaszab.githubuserfinder.model.domain.GitHubUser

@Composable
fun GitHubProjectRow(item: GitHubUser, onItemCLick: (String) -> Unit) {
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
                Image(
                    painter = rememberImagePainter(item.avatarUrl),
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
        }
    }
}
