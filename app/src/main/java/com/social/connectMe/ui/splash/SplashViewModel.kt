package com.social.connectMe.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    private val _navigationEvent = MutableSharedFlow<SplashNavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            delay(2000) // Simulate splash delay
            // Here you would check if user is logged in
            _navigationEvent.emit(SplashNavigationEvent.NavigateToLogin)
        }
    }
}

sealed class SplashNavigationEvent {
    object NavigateToLogin : SplashNavigationEvent()
    object NavigateToDashboard : SplashNavigationEvent()
}
