package com.social.connectMe.ui.theme

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

private val Context.dataStore by preferencesDataStore(
    name = "theme_prefs"
)

