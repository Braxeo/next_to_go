package com.brandonkitt.core.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Orange80,
    onPrimary = Color.White,
    secondary = Color.White,
    onSecondary = Color.White,
    tertiary = Color.White,
    onTertiary = DarkerOrange,
    background = DarkerOrange,
    onBackground = Color.White,
    surface = Orange80,
    onSurface = Color.White,
)
private val LightColorScheme = lightColorScheme(
    primary = Orange40,
    onPrimary = Color.Black,
    secondary = OrangeGrey40,
    onSecondary = Color.White,
    tertiary = White40,
    onTertiary = Color.White,
    background = Color.White,
    onBackground = Color.Black,
    surface = Orange40,
    onSurface = Color.Black,
    /* Other default colors to override

    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)
@Composable
fun NextToGoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}