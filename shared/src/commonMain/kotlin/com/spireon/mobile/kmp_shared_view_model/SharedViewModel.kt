package com.spireon.mobile.kmp_shared_view_model

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class SharedViewModel {
    private val scope: CoroutineScope = MainScope()

    private val _state = MutableStateFlow("Initial Value 1")
    val state: StateFlow<String> = _state

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun setState(newValue: String) {
        _state.value = newValue
        println("Current state value: ${state.value}")
    }

    fun setLoading(loading: Boolean) {
        _isLoading.value = loading
        println("Current isLoading value: ${isLoading.value}")
    }

    fun updateIsLoadingAfterDelay() {
        scope.launch {
            _isLoading.value = true
            delay(3000L)
            _isLoading.value = false
        }
    }

    fun observeCombinedState(callback: (String, Boolean) -> Unit) {
        scope.launch {
            combine(state, isLoading) { state,  isLoading ->
                Pair(state, isLoading)
            }.collect { (state, isLoading) ->
                callback(state, isLoading)
            }
        }
    }

    fun clear() {
        scope.cancel()
        println("SharedViewModel MainScope cancelled")
    }

    data class Pair<A, B>(val first: A, val second: B)
}