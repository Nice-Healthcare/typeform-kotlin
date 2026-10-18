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
    /**
     * Padding applied to individual container components - such as the 'intermittent' radio/check buttons.
     */
    val elementPadding: PaddingValues = PaddingValues(all = 10.dp),
    val headlineContainerSpacing: Dp = 30.dp,
    val contentContainerSpacing: Dp = 15.dp,
    val elementSpacing: Dp = 10.dp,
    val buttonShape: Shape = RoundedCornerShape(size = 8.dp),
)
