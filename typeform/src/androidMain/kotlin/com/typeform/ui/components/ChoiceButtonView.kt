package com.typeform.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.typeform.ui.models.Settings

@Composable
internal fun ChoiceButtonView(
    settings: Settings,
    text: String,
    allowMultiple: Boolean = false,
    selected: Boolean = false,
    onClick: () -> Unit,
) {
    val background = if (selected) settings.interaction.selectedBackgroundColor else settings.interaction.unselectedBackgroundColor
    val border = if (selected) settings.interaction.selectedStrokeColor else settings.interaction.unselectedStrokeColor

    Button(
        onClick = onClick,
        elevation = null,
        border = BorderStroke(
            width = settings.interaction.unselectedStrokeWidth,
            color = border,
        ),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = background,
        ),
        contentPadding = PaddingValues(),
    ) {
        Row(
            modifier = Modifier.padding(settings.interaction.padding),
            horizontalArrangement = Arrangement.spacedBy(settings.interaction.horizontalSpacing),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ChoiceIndicatorView(
                settings = settings,
                allowMultiple = allowMultiple,
                selected = selected,
            )

            StyledTextView(
                text = text,
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.body1,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChoiceButtonViewPreview() {
    ChoiceButtonView(
        settings = Settings(),
        text = "Example Button",
    ) {
    }
}
