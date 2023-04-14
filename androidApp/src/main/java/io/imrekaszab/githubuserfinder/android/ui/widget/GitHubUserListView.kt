package io.imrekaszab.githubuserfinder.android.ui.widget

import androidx.compose.runtime.Composable
import io.imrekaszab.githubuserfinder.model.domain.GitHubUser

@Composable
fun GitHubUserListView(
    itemList: List<GitHubUser>,
    showFavouriteIconOnItem: Boolean = false,
    isFetchingFinished: Boolean = true,
    loadMore: () -> Unit = {},
    onItemClick: (String) -> Unit = {}
) {
    InfiniteLoadingListView(
        items = itemList,
        isFetchingFinished = isFetchingFinished,
        loadMore = { loadMore() }
    ) { _, item ->
        GitHubUserRow(item = item as GitHubUser, showFavouriteIconOnItem) { userName ->
            onItemClick(userName)
        }
    }
}
