package com.social.connectMe.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.social.connectMe.domain.usecase.GetThemeUseCase
import com.social.connectMe.domain.usecase.SaveThemeUseCase
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
    private val getThemeUseCase: GetThemeUseCase,
    private val saveThemeUseCase: SaveThemeUseCase
) : ViewModel() {

    val uiState: StateFlow<SettingsState> = getThemeUseCase()
        .map { SettingsState(themeMode = it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SettingsState()
        )

    fun onThemeChange(isNightMode: Boolean) {
        viewModelScope.launch {
            val newMode = if (isNightMode) ThemeMode.DARK else ThemeMode.LIGHT
            saveThemeUseCase(newMode)
        }
    }
}
