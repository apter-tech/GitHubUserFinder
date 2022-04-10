package io.imrekaszab.githubuserfinder.android.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import io.imrekaszab.githubuserfinder.android.ui.theme.Dimens

@Composable
fun GitHubUserDetailScreen(navController: NavController, userId: Int?) {
    Scaffold(topBar = {
        TopAppBar {
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.padding(start = Dimens.tiny)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Arrow Back",
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    })
            }
        }
    }) { }
}
