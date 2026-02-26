package com.typeform.ui.models

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * General padding & spacing applied across every screen.
 */
data class Presentation(
    val topBarPadding: PaddingValues = PaddingValues(10.dp),
    val contentPadding: PaddingValues = PaddingValues(10.dp),
    val headlineVerticalSpacing: Dp = 30.dp,
    val contentVerticalSpacing: Dp = 15.dp,
    val contentHorizontalSpacing: Dp = 10.dp,
    val containerPadding: PaddingValues = PaddingValues(10.dp),
    val skipWelcomeScreen: Boolean = false,
    val skipEndingScreen: Boolean = false,
)
