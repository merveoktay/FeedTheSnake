package com.example.feedthesnake.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.feedthesnake.R

val customFontFamily = FontFamily(
    Font(R.font.overlock_regular, FontWeight.Normal),
    Font(R.font.overlock_bold, FontWeight.Bold)
)
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = customFontFamily,
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold
    ),
    bodyMedium = TextStyle(
        fontFamily = customFontFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal
    )
)/*
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */*/
