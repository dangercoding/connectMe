package com.social.connectMe.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.social.connectMe.domain.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val locationRepository: LocationRepository
) : ViewModel() {
    private val _state = MutableStateFlow(DashboardState())
    val state = _state.asStateFlow()

    private var isObservingLocation = false

    fun observeLocationUpdates() {
        if (isObservingLocation) return
        isObservingLocation = true

        locationRepository.getLocationUpdates()
            .onEach { location ->
                _state.update { it.copy(
                    latitude = location.latitude,
                    longitude = location.longitude,
                    locationError = null
                ) }
            }
            .launchIn(viewModelScope)
    }

    fun refreshLocation() {
        viewModelScope.launch {
            locationRepository.getCurrentLocation()
                .onSuccess { location ->
                    _state.update { it.copy(
                        latitude = location.latitude,
                        longitude = location.longitude,
                        locationError = null
                    ) }
                }
                .onFailure { error ->
                    _state.update { it.copy(locationError = error.message) }
                }
        }
    }
}

data class DashboardState(
    val welcomeMessage: String = "Welcome to ConnectMe!",
    val latitude: Double? = null,
    val longitude: Double? = null,
    val locationError: String? = null
)
