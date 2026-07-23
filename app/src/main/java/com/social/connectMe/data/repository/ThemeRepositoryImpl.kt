package com.social.connectMe.data.repository

import com.social.connectMe.data.local.datastore.ThemePreferences
import com.social.connectMe.domain.repository.ThemeRepository
import com.social.connectMe.ui.theme.ThemeMode
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ThemeRepositoryImpl @Inject constructor(
    private val themePreferences: ThemePreferences
) : ThemeRepository {
    override val themeMode: Flow<ThemeMode> = themePreferences.themeMode

    override suspend fun saveTheme(mode: ThemeMode) {
        themePreferences.saveTheme(mode)
    }
}
