package com.typeform.ui.models

import androidx.compose.material.SliderColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color

data class TypeformSliderColors(
    val thumbColor: Color = Color.Blue,
    val tickColor: Color = Color.Black,
    val trackColor: Color = Color.LightGray,
) : SliderColors {

    companion object {
        val opinionScaleColors: TypeformSliderColors = TypeformSliderColors()
    }

    @Composable
    override fun thumbColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(thumbColor)
    }

    @Composable
    override fun tickColor(enabled: Boolean, active: Boolean): State<Color> {
        return rememberUpdatedState(tickColor)
    }

    @Composable
    override fun trackColor(enabled: Boolean, active: Boolean): State<Color> {
        return rememberUpdatedState(trackColor)
    }
}