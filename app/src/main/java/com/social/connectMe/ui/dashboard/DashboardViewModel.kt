package com.social.connectMe.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.social.connectMe.domain.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.sample
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

    @OptIn(FlowPreview::class)
    fun observeLocationUpdates(intervalMillis: Long = 10L) {
        if (isObservingLocation) return
        isObservingLocation = true
        
        _state.update { it.copy(isPermissionDenied = false) }

        locationRepository.getLocationUpdates()
            .sample(intervalMillis)
            .onEach { location ->
                _state.update { it.copy(
                    latitude = location.latitude,
                    longitude = location.longitude,
                    locationError = null
                ) }
            }
            .launchIn(viewModelScope)
    }

    fun onPermissionDenied() {
        _state.update { it.copy(isPermissionDenied = true) }
    }

    fun refreshLocation() {
        viewModelScope.launch {
            locationRepository.getCurrentLocation()
                .onSuccess { location ->
                    _state.update { it.copy(
                        latitude = location.latitude,
                        longitude = location.longitude,
                        locationError = null,
                        isPermissionDenied = false
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
    val locationError: String? = null,
    val isPermissionDenied: Boolean = false
)
