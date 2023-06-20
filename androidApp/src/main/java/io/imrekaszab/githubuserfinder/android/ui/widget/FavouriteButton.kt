package io.imrekaszab.githubuserfinder.android.ui.widget

import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import io.imrekaszab.githubuserfinder.MR
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
            painter = painterResource(
                imageResource = if (isFavourite) {
                    MR.images.ic_star_fill
                } else {
                    MR.images.ic_star
                }
            ),
            contentDescription = stringResource(MR.strings.favourite_button_content_description)
        )
    }
}
