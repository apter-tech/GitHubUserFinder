package io.imrekaszab.githubuserfinder.android.ui.widget

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun InfiniteLoadingListView(
    items: List<Any>,
    loadMore: () -> Unit,
    isFetchingFinished: Boolean,
    rowContent: @Composable (Int, Any) -> Unit
) {
    val listState = rememberLazyListState()
    val firstVisibleIndex = remember { mutableStateOf(listState.firstVisibleItemIndex) }
    LazyColumn(state = listState) {
        itemsIndexed(items) { index, item ->
            if (items.size == index + 1 && !isFetchingFinished) {
                rowContent(index, item)
                LoadingView()
            } else {
                rowContent(index, item)
            }
        }
    }
    if (listState.shouldLoadMore(firstVisibleIndex) && !isFetchingFinished) {
        SideEffect {
            loadMore()
        }
    }
}

fun LazyListState.shouldLoadMore(rememberedIndex: MutableState<Int>): Boolean {
    val firstVisibleIndex = this.firstVisibleItemIndex
    if (rememberedIndex.value != firstVisibleIndex) {
        rememberedIndex.value = firstVisibleIndex
        return layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
    }
    return false
}
