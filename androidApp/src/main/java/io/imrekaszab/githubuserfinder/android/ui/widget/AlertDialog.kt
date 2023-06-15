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
import androidx.compose.ui.res.stringResource
import io.imrekaszab.githubuserfinder.android.R
import io.imrekaszab.githubuserfinder.android.ui.theme.Dimens

@Composable
fun RemoveAllUserDialog(onPositiveButtonClick: () -> Unit, onDismissRequest: () -> Unit) {
    AlertDialog(
        title = {
            Text(text = stringResource(id = R.string.remove_all_user_dialog_title))
        },
        text = {
            Text(text = stringResource(id = R.string.remove_all_user_dialog_description))
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
                    Text(stringResource(id = R.string.remove_all_user_dialog_dismiss))
                }
                Button(
                    modifier = Modifier.padding(all = Dimens.tiny),
                    onClick = onPositiveButtonClick
                ) {
                    Text(stringResource(id = R.string.remove_all_user_dialog_remove))
                }
            }
        },
        onDismissRequest = onDismissRequest
    )
}
