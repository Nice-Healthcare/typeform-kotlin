package com.typeform.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.typeform.ui.LocalColors
import com.typeform.ui.LocalPresentation

@Composable
internal fun CallToActionContainerView(content: @Composable ColumnScope.() -> Unit) {
    val presentation = LocalPresentation.current
    val colors = LocalColors.current

    Surface(
        color = colors.surface,
    ) {
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = colors.divider,
        )

        Column(
            modifier = Modifier.padding(presentation.contentPadding),
        ) {
            content()
        }
    }
}
