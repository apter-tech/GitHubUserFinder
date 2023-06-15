package io.imrekaszab.githubuserfinder.android.ui.widget

import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import io.imrekaszab.githubuserfinder.android.R
import io.imrekaszab.githubuserfinder.android.ui.theme.Dimens

@Composable
fun FavoriteButton(
    modifier: Modifier,
    isFavourite: Boolean,
    onFavouriteClick: (Boolean) -> Unit
) {
    IconToggleButton(
        modifier = modifier,
        checked = isFavourite,
        onCheckedChange = { onFavouriteClick(!isFavourite) }
    ) {
        Icon(
            tint = Color.White,
            modifier = Modifier.graphicsLayer {
                scaleX = Dimens.favouriteButtonScale
                scaleY = Dimens.favouriteButtonScale
            },
            imageVector = if (isFavourite) {
                Icons.Filled.Favorite
            } else {
                Icons.Default.FavoriteBorder
            },
            contentDescription = stringResource(id = R.string.favourite_button_content_description)
        )
    }
}
