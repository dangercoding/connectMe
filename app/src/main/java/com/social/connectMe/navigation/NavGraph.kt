package com.social.connectMe.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.social.connectMe.ui.login.LoginScreen
import com.social.connectMe.ui.settings.SettingsScreen
import com.social.connectMe.ui.dashboard.DashboardScreen
import com.social.connectMe.ui.splash.SplashScreen
import kotlinx.serialization.Serializable

@Serializable
data object SplashDestination

@Serializable
data object LoginDestination

@Serializable
data object DashboardDestination

@Serializable
data object SettingsDestination

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = SplashDestination,
        modifier = modifier
    ) {
        composable<SplashDestination> {
            SplashScreen(
                onNavigateToLogin = {
                    navController.navigate(LoginDestination) {
                        popUpTo(SplashDestination) { inclusive = true }
                    }
                },
                onNavigateToDashboard = {
                    navController.navigate(DashboardDestination) {
                        popUpTo(SplashDestination) { inclusive = true }
                    }
                }
            )
        }

        composable<LoginDestination> {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(DashboardDestination) {
                        popUpTo(LoginDestination) { inclusive = true }
                    }
                }
            )
        }

        composable<DashboardDestination> {
            DashboardScreen(
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
