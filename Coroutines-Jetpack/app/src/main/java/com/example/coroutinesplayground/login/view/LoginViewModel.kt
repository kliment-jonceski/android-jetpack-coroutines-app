package com.example.coroutinesplayground.login.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutinesplayground.login.domain.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class LoginViewModel(
    private val loginUseCase: LoginUseCase
): ViewModel() {
    private val _state: MutableStateFlow<LoginState> = MutableStateFlow(LoginState.Initial)
    val state: StateFlow<LoginState> = _state

    fun onEvent (event: Event) {
        viewModelScope.launch {
            when (event) {
                is Event.OnSubmitLogin -> submitLogin(event.username, event.password)
                else -> {}
            }
        }
    }

    private suspend fun submitLogin (username: String, password: String) {
        _state.emit(LoginState.InProgress)
        Timber.d("Login started, In progress....")
        val result = loginUseCase.login(username, password)
        if (result.isSuccess) {
            Timber.d("Login finished Successfully")
            _state.update { LoginState.Success }
        } else {
            Timber.d("Login Failure ${LoginState.Failure}")
            _state.update { LoginState.Failure }
        }
    }

    sealed class LoginState {
        object Initial: LoginState()
        object InProgress: LoginState()
        object Success: LoginState()
        object Failure: LoginState()
    }

    sealed class Event {
        data class OnSubmitLogin(val username: String, val password: String) : Event()
    }
}