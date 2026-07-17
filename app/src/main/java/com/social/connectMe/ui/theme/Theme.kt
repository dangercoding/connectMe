package com.social.connectMe.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

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

    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        // typography = AppTypography,
        content = content
    )
}
