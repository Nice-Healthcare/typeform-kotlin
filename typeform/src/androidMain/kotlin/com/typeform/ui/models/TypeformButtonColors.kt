package com.typeform.ui.models

import androidx.compose.material.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color

data class TypeformButtonColors(
    val backgroundColor: Color = Color.White,
    val contentColor: Color = Color.Black,
    val disabledBackgroundColor: Color = Color.LightGray,
    val disabledContentColor: Color = Color.DarkGray,
) : ButtonColors {

    companion object {
        val defaultColors: TypeformButtonColors = TypeformButtonColors()

        val ratingColors: TypeformButtonColors = TypeformButtonColors(
            backgroundColor = Color.Black,
            contentColor = Color.Blue,
        )
    }

    @Composable
    override fun backgroundColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(if (enabled) backgroundColor else disabledBackgroundColor)
    }

    @Composable
    override fun contentColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(if (enabled) contentColor else disabledContentColor)
    }
}
