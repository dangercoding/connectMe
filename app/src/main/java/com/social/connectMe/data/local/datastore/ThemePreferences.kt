package com.social.connectMe.data.local.datastore
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.social.connectMe.ui.theme.ThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(
    name = "theme_prefs"
)

class ThemePreferences(
    private val context: Context
) {

    companion object {

        private val THEME_KEY =
            stringPreferencesKey("theme_mode")
    }

    val themeMode: Flow<ThemeMode> =
        context.dataStore.data.map { preferences ->

            when (
                preferences[THEME_KEY]
            ) {

                "DARK" -> ThemeMode.DARK

                "LIGHT" -> ThemeMode.LIGHT

                else -> ThemeMode.SYSTEM
            }
        }

    suspend fun saveTheme(
        themeMode: ThemeMode
    ) {

        context.dataStore.edit { preferences ->

            preferences[THEME_KEY] =
                themeMode.name
        }
    }
}