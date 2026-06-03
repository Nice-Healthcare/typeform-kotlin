package com.typeform.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.typeform.ui.LocalTextStyles
import com.typeform.ui.models.TextStyles
import com.typeform.ui.preview.MaterialThemePreview

@Composable
internal fun TextView(
    modifier: Modifier = Modifier,
    text: String,
    typeStyle: TextStyles.TypeStyle,
    textAlign: TextAlign? = null,
) {
    val lettering = LocalTextStyles.current

    Text(
        text = text,
        modifier = modifier,
        textAlign = textAlign,
        style = lettering.textStyle(typeStyle),
    )
}

@PreviewLightDark
@Composable
private fun StyledTextViewPreview() {
    MaterialThemePreview {
        Column {
            TextView(
                text = "Display Style (frm. Title)",
                typeStyle = TextStyles.TypeStyle.DISPLAY,
            )
            TextView(
                text = "Headline Style (frm. Subtitle)",
                typeStyle = TextStyles.TypeStyle.HEADLINE,
            )
            TextView(
                text = "Title Style (frm. Prompt)",
                typeStyle = TextStyles.TypeStyle.TITLE,
            )
            TextView(
                text = "Body Style",
                typeStyle = TextStyles.TypeStyle.BODY,
            )
            TextView(
                text = "Label Style (frm. Caption)",
                typeStyle = TextStyles.TypeStyle.LABEL,
            )
        }
    }
}
