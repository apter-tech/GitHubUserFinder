package io.imrekaszab.githubuserfinder.util.mvi

import io.imrekaszab.githubuserfinder.util.FlowAdapter
import io.imrekaszab.githubuserfinder.util.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class Reducer<S : UiState, E : UiEvent>(initialVal: S) : ViewModel() {
    protected val mainScope = viewModelScope

    private val _state: MutableStateFlow<S> = MutableStateFlow(initialVal)
    val state: StateFlow<S>
        get() = _state

    val stateFlowAdapter = state.asCallbacks()

    fun sendEvent(event: E) {
        reduce(_state.value, event)
    }

    suspend fun setState(newState: S) {
        _state.emit(newState)
    }

    abstract fun reduce(oldState: S, event: E)

    private fun <T : Any> StateFlow<T>.asCallbacks() = FlowAdapter(viewModelScope, this)
}

interface UiState

interface UiEvent
