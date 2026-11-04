package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = TurquoisePrimary,
    onPrimary = TurquoiseOnPrimary,
    primaryContainer = TurquoisePrimaryContainer,
    onPrimaryContainer = TurquoiseOnPrimaryContainer,
    secondary = TurquoiseSecondary,
    secondaryContainer = TurquoiseSecondaryContainer,
    onSecondaryContainer = TurquoiseOnSecondaryContainer,
    background = TurquoiseBackground,
    onBackground = TextPrimary,
    surface = TurquoiseSurface,
    onSurface = TextPrimary,
    surfaceVariant = TurquoiseSurfaceVariant,
    onSurfaceVariant = TextSecondary,
    error = ResetRed
)

private val DarkColorScheme = darkColorScheme(
    primary = TurquoiseSecondary,
    onPrimary = Color.Black,
    primaryContainer = TurquoisePrimary,
    onPrimaryContainer = Color.White,
    secondary = TurquoisePrimaryContainer,
    background = Color(0xFF0F2027),
    onBackground = Color.White,
    surface = Color(0xFF1B3B48),
    onSurface = Color.White
)

@Composable
fun PriceComparatorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}
