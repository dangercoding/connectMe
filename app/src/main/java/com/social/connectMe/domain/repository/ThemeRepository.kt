package com.social.connectMe.domain.repository

import com.social.connectMe.ui.theme.ThemeMode
import kotlinx.coroutines.flow.Flow

interface ThemeRepository {
    val themeMode: Flow<ThemeMode>
    suspend fun saveTheme(mode: ThemeMode)
}
