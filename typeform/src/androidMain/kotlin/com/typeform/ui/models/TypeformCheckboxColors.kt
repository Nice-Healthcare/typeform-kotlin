package com.typeform.ui.models

import androidx.compose.material.CheckboxColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.state.ToggleableState

data class TypeformCheckboxColors(
    val uncheckedBorderColor: Color = Color.Black,
    val checkedBorderColor: Color = Color.Blue,
    val uncheckedBoxColor: Color = Color.White,
    val checkedBoxColor: Color = Color.Blue,
    val checkmarkColor: Color = Color.White,
) : CheckboxColors {
    @Composable
    override fun borderColor(enabled: Boolean, state: ToggleableState): State<Color> {
        val color = if (state == ToggleableState.On) checkedBorderColor else uncheckedBorderColor
        return rememberUpdatedState(color)
    }

    @Composable
    override fun boxColor(enabled: Boolean, state: ToggleableState): State<Color> {
        val color = if (state == ToggleableState.On) checkedBoxColor else uncheckedBoxColor
        return rememberUpdatedState(color)
    }

    @Composable
    override fun checkmarkColor(state: ToggleableState): State<Color> {
        return rememberUpdatedState(checkmarkColor)
    }
}
