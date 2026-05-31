package com.social.connectMe.ui.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)
