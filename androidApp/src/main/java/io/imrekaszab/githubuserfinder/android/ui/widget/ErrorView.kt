package io.imrekaszab.githubuserfinder.android.ui.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import dev.icerock.moko.resources.compose.stringResource
import io.imrekaszab.githubuserfinder.MR

@Composable
fun ErrorView(errorText: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(MR.strings.error_view_title, errorText),
            textAlign = TextAlign.Center
        )
    }
}
