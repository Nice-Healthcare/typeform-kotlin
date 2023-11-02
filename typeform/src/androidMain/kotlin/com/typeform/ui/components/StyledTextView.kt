package com.typeform.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview

@Composable
internal fun StyledTextView(
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle,
    color: Color? = null,
) {
    Text(
        text = text,
        modifier = modifier,
        color = color ?: textStyle.color,
        fontSize = textStyle.fontSize,
        fontWeight = textStyle.fontWeight,
        fontFamily = textStyle.fontFamily,
    )
}

@Preview(showBackground = true)
@Composable
private fun StyledTextViewPreview() {
    Column {
        StyledTextView(
            text = "Title Style",
            textStyle = MaterialTheme.typography.h4,
        )
        StyledTextView(
            text = "Prompt Style",
            textStyle = MaterialTheme.typography.h5,
        )
        StyledTextView(
            text = "Body Style",
            textStyle = MaterialTheme.typography.body1,
        )
        StyledTextView(
            text = "Caption Style",
            textStyle = MaterialTheme.typography.caption,
        )
    }
}
