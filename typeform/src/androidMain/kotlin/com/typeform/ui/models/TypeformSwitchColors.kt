package com.typeform.ui.models

import androidx.compose.material.SwitchColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color

data class TypeformSwitchColors(
    val uncheckedThumbColor: Color = Color.Black,
    val checkedThumbColor: Color = Color.Blue,
    val uncheckedTrackColor: Color = Color.LightGray,
    val checkedTrackColor: Color = Color.Gray,
) : SwitchColors {
    @Composable
    override fun thumbColor(
        enabled: Boolean,
        checked: Boolean,
    ): State<Color> {
        val color = if (checked) checkedThumbColor else uncheckedThumbColor
        return rememberUpdatedState(color)
    }

    @Composable
    override fun trackColor(
        enabled: Boolean,
        checked: Boolean,
    ): State<Color> {
        val color = if (checked) checkedTrackColor else uncheckedTrackColor
        return rememberUpdatedState(color)
    }
}
