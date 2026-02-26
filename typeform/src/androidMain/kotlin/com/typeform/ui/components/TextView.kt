package com.typeform.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.typeform.ui.LocalAppearance
import com.typeform.ui.models.Appearance
import com.typeform.ui.preview.MaterialThemePreview

@Composable
internal fun TextView(
    modifier: Modifier = Modifier,
    text: String,
    typeStyle: Appearance.TypeStyle,
    textAlign: TextAlign? = null,
) {
    val appearance = LocalAppearance.current

    Text(
        text = text,
        modifier = modifier,
        textAlign = textAlign,
        style = appearance.textStyle(typeStyle),
    )
}

@PreviewLightDark
@Composable
private fun StyledTextViewPreview() {
    MaterialThemePreview {
        Column {
            TextView(
                text = "Display Style (frm. Title)",
                typeStyle = Appearance.TypeStyle.DISPLAY,
            )
            TextView(
                text = "Headline Style (frm. Subtitle)",
                typeStyle = Appearance.TypeStyle.HEADLINE,
            )
            TextView(
                text = "Title Style (frm. Prompt)",
                typeStyle = Appearance.TypeStyle.TITLE,
            )
            TextView(
                text = "Body Style",
                typeStyle = Appearance.TypeStyle.BODY,
            )
            TextView(
                text = "Label Style (frm. Caption)",
                typeStyle = Appearance.TypeStyle.LABEL,
            )
        }
    }
}
