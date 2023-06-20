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
import dev.icerock.moko.resources.compose.stringResource
import io.imrekaszab.githubuserfinder.MR
import io.imrekaszab.githubuserfinder.android.ui.theme.Dimens

@Composable
fun RemoveAllUserDialog(onPositiveButtonClick: () -> Unit, onDismissRequest: () -> Unit) {
    AlertDialog(
        title = {
            Text(text = stringResource(MR.strings.remove_all_user_dialog_title))
        },
        text = {
            Text(text = stringResource(MR.strings.remove_all_user_dialog_description))
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
                    Text(stringResource(MR.strings.remove_all_user_dialog_dismiss))
                }
                Button(
                    modifier = Modifier.padding(all = Dimens.tiny),
                    onClick = onPositiveButtonClick
                ) {
                    Text(stringResource(MR.strings.remove_all_user_dialog_remove))
                }
            }
        },
        onDismissRequest = onDismissRequest
    )
}
