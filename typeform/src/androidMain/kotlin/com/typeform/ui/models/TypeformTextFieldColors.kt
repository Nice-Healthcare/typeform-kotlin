package com.typeform.ui.models

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.material.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color

@Deprecated("", ReplaceWith("TypeformTextFieldColors"))
typealias DefaultTextFieldColors = TypeformTextFieldColors

data class TypeformTextFieldColors(
    val backgroundColor: Color = Color.White,
    val textColor: Color = Color.Black,
    val cursorColor: Color = Color.Black,
    val labelColor: Color = Color.Black,
    val placeholderColor: Color = Color.Gray,
    val indicatorColor: Color = Color.Black,
    val leadingIconColor: Color = Color.Black,
    val trailingIconColor: Color = Color.Black,
) : TextFieldColors {
    @Composable
    override fun backgroundColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(backgroundColor)
    }

    @Composable
    override fun cursorColor(isError: Boolean): State<Color> {
        return rememberUpdatedState(cursorColor)
    }

    @Composable
    override fun indicatorColor(enabled: Boolean, isError: Boolean, interactionSource: InteractionSource): State<Color> {
        return rememberUpdatedState(indicatorColor)
    }

    @Composable
    override fun labelColor(enabled: Boolean, error: Boolean, interactionSource: InteractionSource): State<Color> {
        return rememberUpdatedState(labelColor)
    }

    @Composable
    override fun leadingIconColor(enabled: Boolean, isError: Boolean): State<Color> {
        return rememberUpdatedState(leadingIconColor)
    }

    @Composable
    override fun placeholderColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(placeholderColor)
    }

    @Composable
    override fun textColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(textColor)
    }

    @Composable
    override fun trailingIconColor(enabled: Boolean, isError: Boolean): State<Color> {
        return rememberUpdatedState(trailingIconColor)
    }
}
