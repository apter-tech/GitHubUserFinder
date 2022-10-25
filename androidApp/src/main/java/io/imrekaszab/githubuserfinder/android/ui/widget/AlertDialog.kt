package io.imrekaszab.githubuserfinder.android.ui.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import io.imrekaszab.githubuserfinder.android.ui.theme.Dimens

@Composable
fun RemoveAllUserDialog(onPositiveButtonClick: () -> Unit, onDismissRequest: () -> Unit) {
    AlertDialog(
        title = {
            Text(text = "Remove all user")
        },
        text = {
            Text(text = "Are you sure?")
        },
        backgroundColor = Color.Transparent,
        buttons = {
            Row(
                modifier = Modifier
                    .padding(all = Dimens.tiny)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    modifier = Modifier.padding(all = Dimens.tiny),
                    onClick = onDismissRequest
                ) {
                    Text("Dismiss")
                }
                Button(
                    modifier = Modifier.padding(all = Dimens.tiny),
                    onClick = onPositiveButtonClick
                ) {
                    Text("Remove")
                }
            }
        },
        onDismissRequest = onDismissRequest
    )
}
