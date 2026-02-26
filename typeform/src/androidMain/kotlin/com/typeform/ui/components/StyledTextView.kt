package com.typeform.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
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
    val textStyle = appearance.textStyle(typeStyle)

    Text(
        text = text,
        modifier = modifier,
        fontSize = textStyle.fontSize,
        fontWeight = textStyle.fontWeight,
        fontFamily = textStyle.fontFamily,
        textAlign = textAlign,
    )
}

@Deprecated(message = "", replaceWith = ReplaceWith("TextView"))
@Composable
internal fun StyledTextView(
    text: String,
    textStyle: TextStyle,
    modifier: Modifier = Modifier,
    color: Color? = null,
    textAlign: TextAlign? = null,
) {
    val appearance = LocalAppearance.current

    TextView(
        text = text,
        typeStyle = when (textStyle) {
            appearance.display -> Appearance.TypeStyle.DISPLAY
            appearance.headline -> Appearance.TypeStyle.HEADLINE
            appearance.title -> Appearance.TypeStyle.TITLE
            appearance.label -> Appearance.TypeStyle.LABEL
            else -> Appearance.TypeStyle.BODY
        },
        textAlign = textAlign,
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
