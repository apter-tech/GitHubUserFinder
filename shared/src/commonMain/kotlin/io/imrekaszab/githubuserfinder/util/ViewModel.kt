package io.imrekaszab.githubuserfinder.util

import kotlinx.coroutines.CoroutineScope

expect abstract class ViewModel() {
    val viewModelScope: CoroutineScope
    protected open fun onCleared()
    open fun clear()
}
