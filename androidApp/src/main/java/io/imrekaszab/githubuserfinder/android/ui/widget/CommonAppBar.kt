package io.imrekaszab.githubuserfinder.android.ui.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import io.imrekaszab.githubuserfinder.MR
import io.imrekaszab.githubuserfinder.android.ui.theme.Dimens

@Composable
fun CommonAppBar(
    title: String,
    onArrowClick: () -> Unit = {},
    trailing: @Composable (() -> Unit) = {},
) {
    TopAppBar {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = Dimens.tiny)
                .fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(imageResource = MR.images.ic_arrow_left),
                contentDescription = stringResource(MR.strings.common_app_bar_back_arrow_content_description),
                modifier = Modifier.clickable { onArrowClick() }
            )
            Text(
                text = title,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(start = Dimens.default)
            )
            Spacer(modifier = Modifier.weight(1.0f))
            trailing()
        }
    }
}
