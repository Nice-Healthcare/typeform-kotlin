package com.typeform.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.typeform.ui.LocalColors
import com.typeform.ui.LocalPresentation
import com.typeform.ui.models.Script
import com.typeform.ui.preview.MaterialThemePreview

@Composable
internal fun CallToActionContentView(
    title: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
) {
    val presentation = LocalPresentation.current
    val colors = LocalColors.current

    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        enabled = enabled,
        shape = presentation.buttonShape,
        colors = colors.buttonColors,
        contentPadding = presentation.elementPadding,
    ) {
        TextView(
            text = title,
            script = Script.TITLE,
        )
    }
}

@PreviewLightDark
@Composable
private fun CallToActionContentViewPreview() {
    MaterialThemePreview {
        CallToActionContainerView {
            CallToActionContentView(
                title = "Next",
                onClick = { },
            )

            CallToActionContentView(
                title = "Next",
                enabled = false,
                onClick = { },
            )
        }
    }
}
