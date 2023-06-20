package io.imrekaszab.githubuserfinder.util.reducer

import io.imrekaszab.githubuserfinder.util.FlowAdapter
import io.imrekaszab.githubuserfinder.util.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class Reducer<S : UiState, E : UiEvent>(initialVal: S) : ViewModel() {
    protected val mainScope = viewModelScope

    private val _state: MutableStateFlow<S> = MutableStateFlow(initialVal)
    val state: StateFlow<S>
        get() = _state

    private val _error: MutableStateFlow<String?> = MutableStateFlow(null)
    val error: StateFlow<String?>
        get() = _error

    val stateFlowAdapter = state.asCallbacks()
    val errorFlowAdapter = error.asCallbacks()

    fun sendEvent(event: E) {
        mainScope.launch {
            try {
                reduce(_state.value, event)
            } catch (@Suppress("TooGenericExceptionCaught") ex: Throwable) {
                _error.emit(ex.message)
                doOnError()
            }
        }
    }

    private suspend fun clearError() {
        _error.emit(null)
    }

    suspend fun setState(newState: S, withClearError: Boolean = true) {
        if (withClearError) {
            clearError()
        }
        _state.emit(newState)
    }

    open suspend fun doOnError() {}

    abstract suspend fun reduce(oldState: S, event: E)

    private fun <T : Any?> StateFlow<T>.asCallbacks() = FlowAdapter(viewModelScope, this)
}

interface UiState

interface UiEvent
