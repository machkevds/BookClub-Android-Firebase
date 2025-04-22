// File: ui/theme/BookClubTheme.kt

package com.kds.bookclub.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = RustOrange,
    secondary = ForestGreen,
    background = CoffeeBrown,
    surface = CoffeeBrown,
    onPrimary = Beige,
    onSecondary = Beige,
    onBackground = Beige,
    onSurface = Beige
)

private val LightColorScheme = lightColorScheme(
    primary = RustOrange,
    secondary = ForestGreen,
    background = Beige,
    surface = Beige,
    onPrimary = CoffeeBrown,
    onSecondary = CoffeeBrown,
    onBackground = CoffeeBrown,
    onSurface = CoffeeBrown
)

@Composable
fun BookClubTheme(
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
