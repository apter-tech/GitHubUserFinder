package io.imrekaszab.githubuserfinder.util.mvi

import com.rickclephas.kmp.nativecoroutines.NativeCoroutineScope
import io.imrekaszab.githubuserfinder.util.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class Reducer<S : UiState, E : UiEvent>(initialVal: S) : ViewModel() {
    @NativeCoroutineScope
    protected val mainScope = viewModelScope

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
