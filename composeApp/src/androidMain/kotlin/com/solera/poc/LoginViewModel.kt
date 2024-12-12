package com.solera.poc

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoginViewModel(private val sharedViewModel: LoginSharedViewModel) : ViewModel() {

    init {
        viewModelScope.launch {
            sharedViewModel.viewState.collectLatest { state ->

                // React to state changes
            }
        }
    }
}
