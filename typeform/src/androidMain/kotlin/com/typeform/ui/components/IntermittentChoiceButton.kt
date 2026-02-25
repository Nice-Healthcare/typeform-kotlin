package com.typeform.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.typeform.ui.LocalSettings
import com.typeform.ui.preview.ThemePreview

@Composable
internal fun IntermittentChoiceButton(
    text: String,
    modifier: Modifier = Modifier,
    allowMultiple: Boolean = false,
    selected: Boolean = false,
    onClick: () -> Unit,
) {
    val settings = LocalSettings.current
    val borderStroke = if (selected) settings.button.selectedBorderStroke else settings.button.unselectedBorderStroke

    Button(
        onClick = onClick,
        modifier = modifier,
        elevation = null,
        border = borderStroke,
        colors = settings.button.colors,
        contentPadding = settings.button.contentPadding,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (allowMultiple) {
                Checkbox(
                    checked = selected,
                    onCheckedChange = {
                        onClick()
                    },
                    colors = settings.checkbox.colors,
                )
            } else {
                RadioButton(
                    selected = selected,
                    onClick = onClick,
                    colors = settings.radio.colors,
                )
            }

            StyledTextView(
                text = text,
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun IntermittentChoiceButtonPreview() {
    ThemePreview {
        Column(
            modifier = Modifier.background(MaterialTheme.colors.background),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            IntermittentChoiceButton(
                text = "Example Button",
                allowMultiple = true,
            ) {
            }

            IntermittentChoiceButton(
                text = "Example Button",
                allowMultiple = true,
                selected = true,
            ) {
            }

            IntermittentChoiceButton(
                text = "Example Button",
                allowMultiple = false,
            ) {
            }

            IntermittentChoiceButton(
                text = "Example Button",
                allowMultiple = false,
                selected = true,
            ) {
            }
        }
    }
}
