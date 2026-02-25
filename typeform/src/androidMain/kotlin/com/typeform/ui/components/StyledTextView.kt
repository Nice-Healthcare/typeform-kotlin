package com.typeform.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.typeform.ui.preview.MaterialThemePreview

@Composable
internal fun StyledTextView(
    text: String,
    textStyle: TextStyle,
    modifier: Modifier = Modifier,
    color: Color? = null,
    textAlign: TextAlign? = null,
) {
    Text(
        text = text,
        modifier = modifier,
        color = color ?: textStyle.color,
        fontSize = textStyle.fontSize,
        fontWeight = textStyle.fontWeight,
        fontFamily = textStyle.fontFamily,
        textAlign = textAlign,
    )
}

@PreviewLightDark
@Composable
private fun StyledTextViewPreview() {
    MaterialThemePreview {
        Column {
            StyledTextView(
                text = "Title Style",
                textStyle = MaterialTheme.typography.titleLarge,
            )
            StyledTextView(
                text = "Subtitle Style",
                textStyle = MaterialTheme.typography.titleSmall,
            )
            StyledTextView(
                text = "Body Style",
                textStyle = MaterialTheme.typography.bodyMedium,
            )
            StyledTextView(
                text = "Prompt Style",
                textStyle = MaterialTheme.typography.bodySmall,
            )
            StyledTextView(
                text = "Caption Style",
                textStyle = MaterialTheme.typography.labelMedium,
            )
        }
    }
}
