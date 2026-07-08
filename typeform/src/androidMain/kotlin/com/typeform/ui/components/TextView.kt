package com.typeform.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.typeform.ui.LocalTextStyles
import com.typeform.ui.models.Script
import com.typeform.ui.preview.MaterialThemePreview

@Composable
internal fun TextView(
    modifier: Modifier = Modifier,
    text: String,
    script: Script,
    textAlign: TextAlign? = null,
) {
    Text(
        text = text,
        modifier = modifier,
        textAlign = textAlign,
        style = LocalTextStyles.current.textStyle(script),
    )
}

@PreviewLightDark
@Composable
private fun StyledTextViewPreview() {
    MaterialThemePreview {
        Column {
            TextView(
                text = "Display Style (frm. Title)",
                script = Script.DISPLAY,
            )
            TextView(
                text = "Headline Style (frm. Subtitle)",
                script = Script.HEADLINE,
            )
            TextView(
                text = "Title Style (frm. Prompt)",
                script = Script.TITLE,
            )
            TextView(
                text = "Body Style",
                script = Script.BODY,
            )
            TextView(
                text = "Label Style (frm. Caption)",
                script = Script.LABEL,
            )
        }
    }
}
