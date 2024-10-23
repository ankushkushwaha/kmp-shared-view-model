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

    private val _viewState = MutableStateFlow(
        ViewState(
            message = "Kotlin",
            count = 2
        )
    )
    val viewState: StateFlow<ViewState> = _viewState

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun setMessage(newMessage: String) {
        _viewState.value = _viewState.value.copy(message = newMessage)
        println("=== Current message value: ${_viewState.value.message}")
    }

    fun setCount(count: Int) {
        _viewState.value = _viewState.value.copy(count = count)
        println("=== Current count value: ${_viewState.value.count}")
    }

    fun setLoading(loading: Boolean) {
        _isLoading.value = loading
        println("=== Current isLoading value: ${isLoading.value}")
    }

    fun updateIsLoadingAfterDelay() {
        scope.launch {
            _isLoading.value = true
            delay(3000L)
            _isLoading.value = false
        }
    }

    fun observeCombinedState(callback: (Boolean, ViewState) -> Unit) {
        scope.launch {
            combine(isLoading, viewState) { isLoading, viewState ->
                Pair(isLoading, viewState)
            }.collect { (isLoading, viewState) ->
                callback(isLoading, viewState)
            }
        }
    }

    fun clear() {
        scope.cancel()
        println("=== SharedViewModel MainScope cancelled")
    }

    data class Pair<A, B, C>(val first: A, val second: B, val third: C)

}

data class ViewState(
    val message: String?  = null,
    val count: Int? = null
) {
    companion object {
        fun create(): ViewState {
            return ViewState()
        }
    }
}


