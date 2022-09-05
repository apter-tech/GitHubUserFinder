package io.imrekaszab.githubuserfinder.android.ui.view

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import io.imrekaszab.githubuserfinder.android.R
import io.imrekaszab.githubuserfinder.android.ui.theme.Dimens
import io.imrekaszab.githubuserfinder.android.ui.widget.EmptyView
import io.imrekaszab.githubuserfinder.android.ui.widget.ErrorView
import io.imrekaszab.githubuserfinder.android.ui.widget.RemoveAllUserDialog
import io.imrekaszab.githubuserfinder.viewmodel.FavouriteUsersViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FavouriteGitHubUsersScreen(navController: NavController) {
    val viewModel = FavouriteUsersViewModel()
    val errorHappened by viewModel.error.collectAsState(initial = null)
    val itemList by viewModel.users.collectAsState(initial = emptyList())
    val showRemoveDialog = remember { mutableStateOf(false) }

    Scaffold(topBar = {
        TopAppBar {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(start = Dimens.tiny)
                    .fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Arrow Back",
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    }
                )
                Text(
                    text = stringResource(id = R.string.favourite_screen_title),
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(start = Dimens.default)
                )
                Spacer(modifier = Modifier.weight(1.0f))
                if (itemList.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Remove all user",
                        modifier = Modifier
                            .clickable { showRemoveDialog.value = true }
                            .padding(end = Dimens.tiny)
                    )
                }
            }
        }
    }) {
        when {
            showRemoveDialog.value -> RemoveAllUserDialog(
                onPositiveButtonClick = {
                    viewModel.deleteAllUser()
                    showRemoveDialog.value = false
                },
                onDismissRequest = {
                    showRemoveDialog.value = false
                }
            )
            !errorHappened.isNullOrEmpty() -> ErrorView(errorHappened)
            itemList.isEmpty() -> EmptyView()
            else -> GitHubUserListView(
                navController = navController,
                itemList = itemList
            )
        }
    }
}
