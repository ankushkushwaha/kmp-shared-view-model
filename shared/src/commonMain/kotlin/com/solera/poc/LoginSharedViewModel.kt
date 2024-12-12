package com.solera.poc
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rickclephas.kmp.observableviewmodel.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginSharedViewModel: ViewModel() {
    private val scope = viewModelScope // CoroutineScope(Dispatchers.Main)

    private val _viewState = MutableStateFlow(
        ViewState(
            username = "",
            password = "",
            isValidCredentials = false,
            isRememberLoginSelected = true,
            isLoading = false,
            errorMessage = null,
            pushToAboutScreen = false,
            pushToHomeScreen = false
        )
    )
    val viewState: StateFlow<ViewState> = _viewState.asStateFlow()

    fun validate() {

        val username = _viewState.value.username
        val password = _viewState.value.password

        if (username != null && password != null) {
            if (username.isNotEmpty() && password.isNotEmpty()) {
                _viewState.value = _viewState.value.copy(isValidCredentials = true)
            } else {
                _viewState.value = _viewState.value.copy(isValidCredentials = false)
            }
        }
    }

    fun updateState(newState: ViewState) {
        scope.launch {
            _viewState.value = _viewState.value.copy(
                username = newState.username,
                password = newState.password,
                isValidCredentials = newState.isValidCredentials,
                isRememberLoginSelected = newState.isRememberLoginSelected,
                isLoading = newState.isLoading,
                errorMessage = newState.errorMessage,
                pushToAboutScreen = newState.pushToAboutScreen,
                pushToHomeScreen = newState.pushToHomeScreen)
        }
   }

    fun observeCombinedState(callback: (ViewState) -> Unit) {
        scope.launch {
            viewState.collect { state ->
                callback(state)

                validate()

                println("State" + _viewState.value)
            }
        }
    }

//    fun clear() {
//        scope.cancel()
//    }

    fun startFetch() {

        scope.launch {

            _viewState.value = _viewState.value.copy(isLoading = true)
//            println("isloading" + _viewState.value.isLoading)

            delay(3000L)
            _viewState.value = _viewState.value.copy(
                isLoading = false,
                pushToHomeScreen = true
            )


//            println("isloading" + _viewState.value.isLoading)
        }
    }
}
