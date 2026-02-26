package com.typeform.ui.models

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

data class Appearance(
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
    enum class TypeStyle {
        DISPLAY,
        HEADLINE,
        TITLE,
        BODY,
        LABEL,
    }

    companion object {
        @Composable
        fun construct(typography: Typography): Appearance {
            return Appearance(
                display = typography.displayMedium,
                headline = typography.headlineMedium,
                title = typography.titleMedium,
                body = typography.bodyMedium,
                label = typography.labelMedium,
            )
        }

        @Composable
        fun construct(typography: androidx.compose.material.Typography): Appearance {
            return Appearance(
                display = typography.h5,
                headline = typography.subtitle1,
                title = typography.body1,
                body = typography.body2,
                label = typography.caption,
            )
        }
    }

    fun textStyle(typeStyle: TypeStyle): TextStyle {
        return when (typeStyle) {
            TypeStyle.DISPLAY -> display
            TypeStyle.HEADLINE -> headline
            TypeStyle.TITLE -> title
            TypeStyle.BODY -> body
            TypeStyle.LABEL -> label
        }
    }
}
