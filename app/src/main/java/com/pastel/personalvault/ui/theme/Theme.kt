package com.pastel.personalvault.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = PastelMintPrimary,
    onPrimary = PastelCardSurface,
    primaryContainer = PastelMint100,
    onPrimaryContainer = PastelForestText,
    secondary = PastelMint400,
    onSecondary = PastelCardSurface,
    background = PastelMintLightest,
    onBackground = PastelForestText,
    surface = PastelCardSurface,
    onSurface = PastelForestText,
    surfaceVariant = PastelMintSubtle,
    onSurfaceVariant = PastelTextSubtle,
    outline = PastelBorder
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkPastelPrimary,
    onPrimary = DarkPastelBg,
    primaryContainer = DarkPastelBorder,
    onPrimaryContainer = DarkPastelText,
    secondary = PastelMint300,
    onSecondary = DarkPastelBg,
    background = DarkPastelBg,
    onBackground = DarkPastelText,
    surface = DarkPastelSurface,
    onSurface = DarkPastelText,
    surfaceVariant = DarkPastelBorder,
    onSurfaceVariant = DarkPastelSubtle,
    outline = DarkPastelBorder
)

@Composable
fun PastelVaultTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
