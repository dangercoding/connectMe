package com.social.connectMe.ui.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isLoggedIn: Boolean = false,
    val isRegistered: Boolean = false,
    val isSuccessful: Boolean = false,
    val token: String? = null,
)
