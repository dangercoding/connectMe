package com.social.connectMe.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.social.connectMe.ui.login.LoginScreen
import com.social.connectMe.ui.settings.SettingsScreen
import kotlinx.serialization.Serializable

@Serializable
object LoginDestination

@Serializable
object SettingsDestination

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = LoginDestination,
        modifier = modifier
    ) {
        composable<LoginDestination> {
            LoginScreen(
                onNavigateToSettings = {
                    navController.navigate(SettingsDestination)
                }
            )
        }
        composable<SettingsDestination> {
            SettingsScreen()
        }
    }
}
