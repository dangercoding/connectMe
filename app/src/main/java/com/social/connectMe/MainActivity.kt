package com.social.connectMe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.social.connectMe.navigation.SetupNavGraph
import com.social.connectMe.ui.settings.SettingsViewModel
import com.social.connectMe.ui.theme.ConnectMeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // 1. Enable Edge-to-Edge to match the system loader's full-screen behavior
        enableEdgeToEdge()

        setContent {
            val state by viewModel.uiState.collectAsState()
            val navController = rememberNavController()
            
            ConnectMeTheme(themeMode = state.themeMode) {
                // 2. Remove Scaffold at this level to prevent padding glitches during Splash transition
                SetupNavGraph(
                    navController = navController,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
