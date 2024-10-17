package com.typeform.ui.models

import androidx.compose.material.RadioButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color

data class TypeformRadioButtonColors(
    val unselectedColor: Color = Color.Black,
    val selectedColor: Color = Color.Blue,
) : RadioButtonColors {
    @Composable
    override fun radioColor(
        enabled: Boolean,
        selected: Boolean,
    ): State<Color> {
        val color = if (selected) selectedColor else unselectedColor
        return rememberUpdatedState(color)
    }
}
