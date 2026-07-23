package com.social.connectMe.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.social.connectMe.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
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
            delay(5)
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
            }.onFailure { exception ->
                _state.update { 
                    it.copy(
                        isLoading = false,
                        error = exception.message ?: "An unknown error occurred"
                    ) 
                }
            }
        }
    }
}
