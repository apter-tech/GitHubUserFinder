package io.imrekaszab.githubuserfinder.android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.imrekaszab.githubuserfinder.android.ui.view.GitHubUserDetailScreen
import io.imrekaszab.githubuserfinder.android.ui.view.GitHubUserListScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = GitHubUserScreens.GitHubUserListScreen.route) {
        composable(route = GitHubUserScreens.GitHubUserListScreen.route) {
            GitHubUserListScreen(navController = navController)
        }
        composable(
            route = GitHubUserScreens.GitHubUserDetailScreen.route + "/{userId}",
            arguments = listOf(navArgument(name = "userId") {
                type = NavType.IntType
            })
        ) { entry ->
            GitHubUserDetailScreen(navController, entry.arguments?.getInt("userId"))
        }
    }
}
