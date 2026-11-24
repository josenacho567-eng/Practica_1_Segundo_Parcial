package com.example.practica_1_segundo_parcial.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFD9534F),
    onPrimary = Color(0xFF2B0908),
    primaryContainer = Color(0xFF5C1D1B),
    onPrimaryContainer = Color(0xFFFFDAD6),
    secondary = Color(0xFFE09F3E),
    onSecondary = Color(0xFF2E1B00),
    tertiary = Color(0xFF6FB1A0),
    background = Color(0xFF141316),
    surface = Color(0xFF1C1B1F)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF9E2A2B),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFDAD6),
    onPrimaryContainer = Color(0xFF410002),
    secondary = Color(0xFFB56E14),
    onSecondary = Color.White,
    tertiary = Color(0xFF335C67),
    background = Color(0xFFFFFBF7),
    surface = Color(0xFFFFFBF7)
)

@Composable
fun Practica_1_Segundo_ParcialTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
