package io.imrekaszab.githubuserfinder.android.ui.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import io.imrekaszab.githubuserfinder.MR
import io.imrekaszab.githubuserfinder.android.ui.theme.Dimens

@Composable
fun SearchAppBar(onSearchCLick: (String) -> Unit, onStarClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.searchBarHeight),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.primary
    ) {
        SearchAppBarContent(onSearchCLick, onStarClick)
    }
}

@Composable
fun SearchAppBarContent(onSearchCLick: (String) -> Unit, onStarClick: () -> Unit) {
    var query: String by rememberSaveable { mutableStateOf("") }
    val showClearIcon by remember { derivedStateOf { query.isNotEmpty() } }
    val focusManager = LocalFocusManager.current
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = query,
            onValueChange = { onQueryChanged ->
                query = onQueryChanged
            },
            trailingIcon = {
                if (showClearIcon) {
                    IconButton(onClick = { query = "" }) {
                        Icon(
                            painter = painterResource(imageResource = MR.images.ic_clear),
                            contentDescription =
                            stringResource(MR.strings.clear_button_content_description)
                        )
                    }
                }
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = Color.White.copy(alpha = ContentAlpha.medium)
            ),
            maxLines = 1,
            placeholder = {
                Text(
                    text = stringResource(MR.strings.search_app_bar_title),
                    style = MaterialTheme.typography.h6
                )
            },
            textStyle = MaterialTheme.typography.h6,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    focusManager.clearFocus()
                    onSearchCLick(query)
                }
            )
        )
        IconButton(onClick = onStarClick) {
            Icon(
                painter = painterResource(imageResource = MR.images.ic_star_fill),
                contentDescription = stringResource(MR.strings.favourite_button_content_description)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchAppBarPreview() {
    SearchAppBar({}, {})
}
