package com.social.connectMe.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.social.connectMe.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<LoginUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEmailChange(email: String) {
        _state.update { it.copy(email = email, error = null) }
    }

    fun onPasswordChange(password: String) {
        _state.update { it.copy(password = password, error = null) }
    }

    fun togglePasswordVisibility() {
        _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun onLoginClick() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            
            val result = loginUseCase(
                email = state.value.email,
                password = state.value.password
            )
            
            result.onSuccess { user ->
                _state.update { 
                    it.copy(
                        isLoading = false,
                        isLoggedIn = true,
                        isSuccessful = true
                    ) 
                }
                _eventFlow.emit(LoginUiEvent.LoginSuccess)
            }.onFailure { exception ->
                val errorMessage = exception.message ?: "An unknown error occurred"
                _state.update { 
                    it.copy(
                        isLoading = false,
                        error = errorMessage
                    ) 
                }
                _eventFlow.emit(LoginUiEvent.ShowSnackbar(errorMessage))
            }
        }
    }
}

sealed class LoginUiEvent {
    data class ShowSnackbar(val message: String) : LoginUiEvent()
    object LoginSuccess : LoginUiEvent()
}
