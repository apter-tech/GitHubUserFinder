package io.imrekaszab.githubuserfinder.android.ui.navigation

sealed class GitHubUserScreens(val route: String) {
    object GitHubUserListScreen : GitHubUserScreens("list_screen")
    object GitHubUserDetailScreen : GitHubUserScreens("detail_screen")
    object FavouriteGitHubUsersScreen : GitHubUserScreens("favourite_users_screen")
}
