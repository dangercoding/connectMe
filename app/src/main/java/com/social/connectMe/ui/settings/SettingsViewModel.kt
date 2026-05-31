package com.social.connectMe.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.social.connectMe.data.local.datastore.ThemePreferences
import com.social.connectMe.ui.theme.ThemeMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val themePreferences: ThemePreferences
) : ViewModel() {

    val uiState: StateFlow<SettingsState> = themePreferences.themeMode
        .map { SettingsState(themeMode = it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SettingsState()
        )

    fun toggleTheme(isNightMode: Boolean) {
        viewModelScope.launch {
            val newMode = if (isNightMode) ThemeMode.DARK else ThemeMode.LIGHT
            themePreferences.saveTheme(newMode)
        }
    }
}
