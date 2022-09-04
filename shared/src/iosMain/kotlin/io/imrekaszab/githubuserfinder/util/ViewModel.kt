package io.imrekaszab.githubuserfinder.util

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

actual abstract class ViewModel {

    actual val viewModelScope = MainScope()

    protected actual open fun onCleared() {}

    actual open fun clear() {
        onCleared()
        viewModelScope.cancel()
    }
}
