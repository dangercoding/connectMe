package com.social.connectMe.ui.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.social.connectMe.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToDashboard: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {

    val scale = remember {
        Animatable(0.5f)
    }

    LaunchedEffect(Unit) {
        launch {
            scale.animateTo(
                targetValue = 1.2f,
                animationSpec = tween(1000)
            )

            scale.animateTo(
                targetValue = 1.0f,
                animationSpec = tween(800)
            )
        }

        launch {
            viewModel.navigationEvent.collectLatest { event ->
                when (event) {
                    SplashNavigationEvent.NavigateToLogin ->
                        onNavigateToLogin()

                    SplashNavigationEvent.NavigateToDashboard ->
                        onNavigateToDashboard()
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(R.drawable.main_app_logo),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(220.dp)
                .graphicsLayer {
                    scaleX = scale.value
                    scaleY = scale.value
                }
        )
    }
}
