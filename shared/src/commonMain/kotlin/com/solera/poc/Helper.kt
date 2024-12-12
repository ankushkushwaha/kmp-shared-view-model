package com.solera.poc

import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ObservableFlow<T>(private val stateFlow: StateFlow<T>) {
    private val scope = CoroutineScope(Dispatchers.Main)

    fun observe(onChange: (T) -> Unit) {
        scope.launch {
            stateFlow.collect { value ->
                onChange(value)
            }
        }
    }
}

public fun <T> StateFlow<T>.asObservable(): ObservableFlow<T> {
    return ObservableFlow(this)
}
