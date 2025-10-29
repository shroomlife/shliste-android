package com.shroomlife.shliste.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFFF8CCB),
    onPrimary = Color(0xFF3B0021),
    secondary = Color(0xFF402438),
    onSecondary = Color(0xFFFDECF5),
    tertiary = Color(0xFFE490C3),
    background = Color(0xFF121212),
    onBackground = Color(0xFFEDEDED),
    surface = Color(0xFF1E1E1E),
    onSurface = Color(0xFFEDEDED),
    outline = Color(0xFF5E5E5E)
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    onPrimary = Color.White,
    secondary = SecondaryColor,
    onSecondary = Color(0xFF3B0021),
    tertiary = Color(0xFFB84C8F),
    background = Color(0xFFFFFFFF),
    onBackground = Color(0xFF1E1E1E),
    surface = Color(0xFFFDFDFD),
    onSurface = Color(0xFF1E1E1E),
    outline = Color(0xFFDDDDDD)
)

@Composable
fun ShlisteTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
