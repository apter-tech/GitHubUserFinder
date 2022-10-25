package io.imrekaszab.githubuserfinder.android.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightThemeColors = lightColors(
    primary = BlueGray700,
    primaryVariant = BlueGray900,
    onPrimary = Color.White,
    secondary = BlueGray700,
    secondaryVariant = BlueGray900,
    onSecondary = Color.White,
    error = Color.Red,
    onBackground = Color.Black
)

private val DarkThemeColors = darkColors(
    primary = BlueGray300,
    primaryVariant = BlueGray700,
    onPrimary = Color.Black,
    secondary = BlueGray300,
    onSecondary = Color.Black,
    error = Color.Red,
    onBackground = Color.White
)

@Composable
fun GitHubUserFinderTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkThemeColors
    } else {
        LightThemeColors
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
