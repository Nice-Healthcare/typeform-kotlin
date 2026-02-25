package com.typeform.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.typeform.ui.LocalSettings
import com.typeform.ui.models.LocalPresentation
import com.typeform.ui.preview.MaterialThemePreview

@Composable
internal fun IntermittentChoiceButton(
    text: String,
    modifier: Modifier = Modifier,
    allowMultiple: Boolean = false,
    selected: Boolean = false,
    onClick: () -> Unit,
) {
    val settings = LocalSettings.current

    FilledTonalButton(
        onClick = onClick,
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        contentPadding = LocalPresentation.current.containerPadding,
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
                )
            } else {
                RadioButton(
                    selected = selected,
                    onClick = onClick,
                )
            }

            StyledTextView(
                text = text,
                textStyle = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun IntermittentChoiceButtonPreview() {
    MaterialThemePreview {
        Column(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
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
