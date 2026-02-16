package com.typeform.ui.preview

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun ThemePreview(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colors = Colors(
            primary = Color.Blue,
            primaryVariant = Color.Blue,
            secondary = Color.Green,
            secondaryVariant = Color.Green,
            background = if (darkTheme) Color.Black else Color.White,
            surface = Color.LightGray,
            error = Color.Red,
            onPrimary = if (darkTheme) Color.White else Color.Black,
            onSecondary = if (darkTheme) Color.White else Color.Black,
            onBackground = if (darkTheme) Color.White else Color.Black,
            onSurface = if (darkTheme) Color.White else Color.Black,
            onError = Color.White,
            isLight = !darkTheme,
        ),
        typography = Typography(
            defaultFontFamily = FontFamily.Default,
            h5 = TextStyle(
                color = if (darkTheme) Color.White else Color.Black,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
            ),
            subtitle1 = TextStyle(
                color = if (darkTheme) Color.White else Color.Black,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
            ),
            body1 = TextStyle(
                color = if (darkTheme) Color.White else Color.Black,
                fontSize = 16.sp,
            ),
            body2 = TextStyle(
                color = if (darkTheme) Color.LightGray else Color.DarkGray,
                fontSize = 14.sp,
            ),
            caption = TextStyle(
                color = Color.Gray,
                fontSize = 12.sp,
            ),
        ),
        content = content,
    )
}
