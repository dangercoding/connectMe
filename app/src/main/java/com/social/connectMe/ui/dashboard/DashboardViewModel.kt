package com.social.connectMe.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.social.connectMe.domain.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
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

    init {
        // Initial load
        loadNextItems()
    }

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

    fun loadNextItems() {
        if (_state.value.isLoading || _state.value.endReached) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            
            // Simulate network delay for fetching data
            delay(5500)

            val currentSize = _state.value.items.size
            val pageSize = 10
            val nextItems = (currentSize until currentSize + pageSize).toList()
            
            // For demo purposes, we stop at 100 items
            val endReached = currentSize + pageSize >= 100

            _state.update { it.copy(
                items = it.items + nextItems,
                isLoading = false,
                endReached = endReached
            ) }
        }
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
    val isPermissionDenied: Boolean = false,
    val items: List<Int> = emptyList(),
    val isLoading: Boolean = false,
    val endReached: Boolean = false
)
