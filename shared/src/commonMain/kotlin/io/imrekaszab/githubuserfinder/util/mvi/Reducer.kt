package io.imrekaszab.githubuserfinder.util.mvi

import com.rickclephas.kmp.nativecoroutines.NativeCoroutineScope
import io.imrekaszab.githubuserfinder.util.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull

abstract class Reducer<S : UiState, E : UiEvent>(initialVal: S) : ViewModel() {
    @NativeCoroutineScope
    protected val mainScope = viewModelScope

    protected val errorStateFlow = MutableStateFlow<String?>(null)
    val error: Flow<String> = errorStateFlow.filterNotNull()

    private val _state: MutableStateFlow<S> = MutableStateFlow(initialVal)
    val state: StateFlow<S>
        get() = _state

    val timeCapsule: TimeCapsule<S> = TimeTravelCapsule { storedState ->
        _state.tryEmit(storedState)
    }

    init {
        timeCapsule.addState(initialVal)
    }

    fun sendEvent(event: E) {
        reduce(_state.value, event)
    }

    suspend fun setState(newState: S) {
        _state.emit(newState)
        timeCapsule.addState(newState)
    }

    abstract fun reduce(oldState: S, event: E)
}

interface UiState

interface UiEvent
