package com.typeform.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.typeform.ui.preview.ThemePreview

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

@Preview(showBackground = true)
@Composable
private fun StyledTextViewPreview() {
    ThemePreview {
        Column {
            StyledTextView(
                text = "Title Style",
                textStyle = MaterialTheme.typography.h5,
            )
            StyledTextView(
                text = "Subtitle Style",
                textStyle = MaterialTheme.typography.subtitle1,
            )
            StyledTextView(
                text = "Body Style",
                textStyle = MaterialTheme.typography.body1,
            )
            StyledTextView(
                text = "Prompt Style",
                textStyle = MaterialTheme.typography.body2,
            )
            StyledTextView(
                text = "Caption Style",
                textStyle = MaterialTheme.typography.caption,
            )
        }
    }
}
