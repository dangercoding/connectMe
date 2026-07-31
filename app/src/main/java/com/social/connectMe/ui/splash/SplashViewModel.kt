package com.social.connectMe.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.social.connectMe.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _navigationEvent = MutableSharedFlow<SplashNavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            // Optimization: Fetch login status and run splash timer in parallel.
            // Using .first() ensures we wait for the real value from DataStore,
            // eliminating race conditions where .value might return initialValue prematurely.
            val isLoggedInDeferred = async {
                authRepository.isLoggedIn.first()
            }

            // Branding delay
            val timerDeferred = async { delay(2000) }

            val isLoggedIn = isLoggedInDeferred.await()
            timerDeferred.await()

            val destination = if (isLoggedIn) {
                SplashNavigationEvent.NavigateToDashboard
            } else {
                SplashNavigationEvent.NavigateToLogin
            }
            _navigationEvent.emit(destination)
        }
    }
}

sealed class SplashNavigationEvent {
    data object NavigateToLogin : SplashNavigationEvent()
    data object NavigateToDashboard : SplashNavigationEvent()
}
