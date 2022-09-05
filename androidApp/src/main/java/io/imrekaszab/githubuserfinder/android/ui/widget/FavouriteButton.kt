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

@Composable
fun FavoriteButton(
    isFavourite: Boolean,
    modifier: Modifier,
    onFavouriteCLick: (Boolean) -> Unit
) {
    IconToggleButton(
        modifier = modifier,
        checked = isFavourite,
        onCheckedChange = {
            onFavouriteCLick(!isFavourite)
        }
    ) {
        Icon(
            tint = Color.White,
            modifier = Modifier.graphicsLayer {
                scaleX = 1.3f
                scaleY = 1.3f
            },
            imageVector = if (isFavourite) {
                Icons.Filled.Favorite
            } else {
                Icons.Default.FavoriteBorder
            },
            contentDescription = "FavouriteButton"
        )
    }
}
