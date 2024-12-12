package com.solera.poc
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.MutableStateFlow
import com.rickclephas.kmp.observableviewmodel.launch
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asStateFlow

class LoginSharedViewModelLib : ViewModel() {
    private val _viewState = MutableStateFlow(viewModelScope,
        ViewState(
            username = "",
            password = "",
            isValidCredentials = false,
            isRememberLoginSelected = true,
            isLoading = false,
            errorMessage = null,
            pushToAboutScreen = false,
            pushToHomeScreen = false)
    )

    val viewState = _viewState.asStateFlow()
    val viewStateValue: ViewState
        get() = viewState.value

    fun startFetch() {
        viewModelScope.launch {

            _viewState.value = _viewState.value.copy(isLoading = true)
            println("State" + _viewState.value)

            delay(1000L)
            _viewState.value = _viewState.value.copy(
                isLoading = false,
                pushToHomeScreen = true
            )
            println("State" + _viewState.value)
        }
    }

    fun setUsername(usernme: String) {
        _viewState.value = _viewState.value.copy(username = usernme)
        validate()
    }

    fun setPassword(password: String) {
        _viewState.value = _viewState.value.copy(password = password)
        validate()
    }

    fun setPushToHomeScreen(pushToHomeScreen: Boolean) {
        _viewState.value = _viewState.value.copy(pushToHomeScreen = pushToHomeScreen)
        println("State" + _viewState.value)
    }


    fun validate() {

        val username = _viewState.value.username
        val password = _viewState.value.password

        println("State" + _viewState.value)

        if (username != null && password != null) {
            if (username.isNotEmpty() && password.isNotEmpty()) {
                _viewState.value = _viewState.value.copy(isValidCredentials = true)
            } else {
                _viewState.value = _viewState.value.copy(isValidCredentials = false)
            }
        }
    }
}

data class ViewState(
    var username: String? = null,
    var password: String?= null,
    var isLoading: Boolean? = null,
    var errorMessage: String? = null,
    var isValidCredentials: Boolean? = null,
    val isRememberLoginSelected: Boolean? = null,
    val pushToAboutScreen: Boolean? = null,
    val pushToHomeScreen: Boolean? = null,
    val accessToken: String? = null
)
