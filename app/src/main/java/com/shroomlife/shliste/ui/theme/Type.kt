package com.shroomlife.shliste.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.shroomlife.shliste.R

val ZainFontFamily = FontFamily(
    Font(R.font.zain_extralight, FontWeight.ExtraLight),
    Font(R.font.zain_light, FontWeight.Light),
    Font(R.font.zain_regular, FontWeight.Normal),
    Font(R.font.zain_bold, FontWeight.Bold),
    Font(R.font.zain_extrabold, FontWeight.ExtraBold),
    Font(R.font.zain_black, FontWeight.Black),
    Font(R.font.zain_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.zain_lightitalic, FontWeight.Light, FontStyle.Italic)
)

val AppTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = ZainFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    ),
    titleLarge = TextStyle(
        fontFamily = ZainFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = ZainFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = ZainFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = ZainFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp
    )
)
