package io.imrekaszab.githubuserfinder.util

import com.rickclephas.kmp.nativecoroutines.NativeCoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull

open class CommonViewModel : ViewModel() {
    @NativeCoroutineScope
    protected val mainScope = viewModelScope

    protected val errorStateFlow = MutableStateFlow<String?>(null)
    val error: Flow<String> = errorStateFlow.filterNotNull()
}
