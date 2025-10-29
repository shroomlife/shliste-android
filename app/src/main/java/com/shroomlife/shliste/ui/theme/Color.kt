package com.shroomlife.shliste.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val PrimaryColor = Color(0xFFE064B2)
val SecondaryColor = Color(0xFFFDECF5)
val MainGreen = Color(0xFF7FD1AE)

val DefaultLightGray = Color(0xFFE5E7EB)

@Composable
fun MutedTextColor(): Color =
    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
