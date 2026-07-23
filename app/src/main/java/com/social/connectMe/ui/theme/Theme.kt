package com.social.connectMe.ui.theme

import android.app.Activity
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark
)

private val LightColorScheme = lightColorScheme(
    primary = primary,
    onPrimary = onPrimary,
    primaryContainer = primary_Container,
    onPrimaryContainer = onPrimary_Container,
    secondary = secondary,
    onSecondary = onSecondary,
    secondaryContainer = secondary_Container,
    onSecondaryContainer = onSecondary_Container,
    tertiary = tertiary,
    onTertiary = onTertiary,
    tertiaryContainer = tertiary_Container,
    onTertiaryContainer = onTertiary_Container,
    background = background,
    onBackground = onBackground,
    surface = surface,
    onSurface = onSurface,
    surfaceVariant = surface_Variant,
    onSurfaceVariant = onSurface_Variant,
    error = error,
    onError = onError,
    errorContainer = error_Container,
    onErrorContainer = onError_Container,
    outline = outline,
    outlineVariant = outline_Variant,
    inverseOnSurface = inverse_On_Surface,
    inverseSurface = inverse_Surface,
    inversePrimary = inverse_Primary
)

@Composable
fun ConnectMeTheme(
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    content: @Composable () -> Unit
) {
    val darkTheme = when (themeMode) {
        ThemeMode.DARK -> true
        ThemeMode.LIGHT -> false
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }

    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val animatedColorScheme = animateColorScheme(colorScheme)

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = animatedColorScheme,
        // typography = AppTypography,
        content = {
            Surface(
                color = MaterialTheme.colorScheme.background,
                content = content
            )
        }
    )
}

@Composable
private fun animateColorScheme(targetColorScheme: ColorScheme): ColorScheme {
    val animationSpec: AnimationSpec<Color> = tween(durationMillis = 500)
    
    @Composable
    fun animateColor(color: Color, label: String) = animateColorAsState(
        targetValue = color, 
        animationSpec = animationSpec, 
        label = label
    ).value

    return targetColorScheme.copy(
        primary = animateColor(targetColorScheme.primary, "primary"),
        onPrimary = animateColor(targetColorScheme.onPrimary, "onPrimary"),
        primaryContainer = animateColor(targetColorScheme.primaryContainer, "primaryContainer"),
        onPrimaryContainer = animateColor(targetColorScheme.onPrimaryContainer, "onPrimaryContainer"),
        inversePrimary = animateColor(targetColorScheme.inversePrimary, "inversePrimary"),
        secondary = animateColor(targetColorScheme.secondary, "secondary"),
        onSecondary = animateColor(targetColorScheme.onSecondary, "onSecondary"),
        secondaryContainer = animateColor(targetColorScheme.secondaryContainer, "secondaryContainer"),
        onSecondaryContainer = animateColor(targetColorScheme.onSecondaryContainer, "onSecondaryContainer"),
        tertiary = animateColor(targetColorScheme.tertiary, "tertiary"),
        onTertiary = animateColor(targetColorScheme.onTertiary, "onTertiary"),
        tertiaryContainer = animateColor(targetColorScheme.tertiaryContainer, "tertiaryContainer"),
        onTertiaryContainer = animateColor(targetColorScheme.onTertiaryContainer, "onTertiaryContainer"),
        background = animateColor(targetColorScheme.background, "background"),
        onBackground = animateColor(targetColorScheme.onBackground, "onBackground"),
        surface = animateColor(targetColorScheme.surface, "surface"),
        onSurface = animateColor(targetColorScheme.onSurface, "onSurface"),
        surfaceVariant = animateColor(targetColorScheme.surfaceVariant, "surfaceVariant"),
        onSurfaceVariant = animateColor(targetColorScheme.onSurfaceVariant, "onSurfaceVariant"),
        surfaceTint = animateColor(targetColorScheme.surfaceTint, "surfaceTint"),
        inverseSurface = animateColor(targetColorScheme.inverseSurface, "inverseSurface"),
        inverseOnSurface = animateColor(targetColorScheme.inverseOnSurface, "inverseOnSurface"),
        error = animateColor(targetColorScheme.error, "error"),
        onError = animateColor(targetColorScheme.onError, "onError"),
        errorContainer = animateColor(targetColorScheme.errorContainer, "errorContainer"),
        onErrorContainer = animateColor(targetColorScheme.onErrorContainer, "onErrorContainer"),
        outline = animateColor(targetColorScheme.outline, "outline"),
        outlineVariant = animateColor(targetColorScheme.outlineVariant, "outlineVariant"),
        scrim = animateColor(targetColorScheme.scrim, "scrim"),
        surfaceBright = animateColor(targetColorScheme.surfaceBright, "surfaceBright"),
        surfaceDim = animateColor(targetColorScheme.surfaceDim, "surfaceDim"),
        surfaceContainer = animateColor(targetColorScheme.surfaceContainer, "surfaceContainer"),
        surfaceContainerHigh = animateColor(targetColorScheme.surfaceContainerHigh, "surfaceContainerHigh"),
        surfaceContainerHighest = animateColor(targetColorScheme.surfaceContainerHighest, "surfaceContainerHighest"),
        surfaceContainerLow = animateColor(targetColorScheme.surfaceContainerLow, "surfaceContainerLow"),
        surfaceContainerLowest = animateColor(targetColorScheme.surfaceContainerLowest, "surfaceContainerLowest"),
    )
}
