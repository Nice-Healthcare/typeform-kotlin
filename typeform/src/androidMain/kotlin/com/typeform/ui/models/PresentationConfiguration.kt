package com.typeform.ui.models

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class PresentationConfiguration(
    /**
     * Padding applied to the _top navigation bar_ elements.
     */
    val topBarPadding: PaddingValues = PaddingValues(all = 10.dp),
    /**
     * Padding applied to the overall content (main body).
     */
    val contentPadding: PaddingValues = PaddingValues(all = 10.dp),
    @Deprecated(message = "", replaceWith = ReplaceWith("buttonContentPadding"))
    val elementPadding: PaddingValues = PaddingValues(all = 10.dp),
    val headlineContainerSpacing: Dp = 30.dp,
    val contentContainerSpacing: Dp = 15.dp,
    val elementSpacing: Dp = 10.dp,
    val buttonShape: Shape = RoundedCornerShape(size = 8.dp),
    /**
     * Padding applied to the filled/tonal (CTA) buttons.
     */
    val buttonContentPadding: PaddingValues = PaddingValues(horizontal = 8.dp, vertical = 12.dp),
    /**
     * Padding applied to outlined button components - such as the 'intermittent' radio/check buttons.
     */
    val outlinedButtonContentPadding: PaddingValues = PaddingValues(8.dp),
)
