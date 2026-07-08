package com.typeform.ui.models

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * Provides a reference to the typographic styles used in Typeform UI.
 */
data class TextStyles(
    val display: TextStyle = TextStyle.Default.copy(
        fontSize = 45.sp,
        fontWeight = FontWeight(400),
        lineHeight = 52.sp,
    ),
    val headline: TextStyle = TextStyle.Default.copy(
        fontSize = 28.sp,
        fontWeight = FontWeight(400),
        lineHeight = 36.sp,
    ),
    val title: TextStyle = TextStyle.Default.copy(
        fontSize = 16.sp,
        fontWeight = FontWeight(500),
        lineHeight = 24.sp,
    ),
    val body: TextStyle = TextStyle.Default.copy(
        fontSize = 14.sp,
        fontWeight = FontWeight(400),
        lineHeight = 20.sp,
    ),
    val label: TextStyle = TextStyle.Default.copy(
        fontSize = 12.sp,
        fontWeight = FontWeight(500),
        lineHeight = 16.sp,
    ),
) {
    companion object {
        @Composable
        fun construct(typography: Typography): TextStyles {
            return TextStyles(
                display = typography.displayMedium,
                headline = typography.headlineMedium,
                title = typography.titleMedium,
                body = typography.bodyMedium,
                label = typography.labelMedium,
            )
        }

        @Composable
        fun construct(typography: androidx.compose.material.Typography): TextStyles {
            return TextStyles(
                display = typography.h5,
                headline = typography.subtitle1,
                title = typography.body1,
                body = typography.body2,
                label = typography.caption,
            )
        }
    }

    fun textStyle(script: Script): TextStyle {
        return when (script) {
            Script.DISPLAY -> display
            Script.HEADLINE -> headline
            Script.TITLE -> title
            Script.BODY -> body
            Script.LABEL -> label
        }
    }
}
