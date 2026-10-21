package com.typeform.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.typeform.ui.LocalColors
import com.typeform.ui.LocalPresentation
import com.typeform.ui.models.Script
import com.typeform.ui.preview.MaterialThemePreview

@Composable
internal fun IntermittentChoiceButton(
    text: String,
    modifier: Modifier = Modifier,
    allowMultiple: Boolean = false,
    selected: Boolean = false,
    onClick: () -> Unit,
) {
    val presentation = LocalPresentation.current
    val colors = LocalColors.current

    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        shape = presentation.buttonShape,
        colors = colors.outlinedButtonColors(selected),
        border = BorderStroke(
            width = if (selected) 2.dp else 1.dp,
            color = if (selected) colors.outlinedButton.selectedStrokeColor else colors.outlinedButton.unselectedStrokeColor,
        ),
        contentPadding = presentation.outlinedButtonContentPadding,
    ) {
        if (allowMultiple) {
            Checkbox(
                checked = selected,
                onCheckedChange = {
                    onClick()
                },
                colors = colors.checkboxColors,
            )
        } else {
            RadioButton(
                selected = selected,
                onClick = onClick,
                colors = colors.radioButtonColors,
            )
        }

        TextView(
            modifier = Modifier.weight(1f),
            text = text,
            script = Script.TITLE,
        )
    }
}

@PreviewLightDark
@Composable
private fun IntermittentChoiceButtonPreview() {
    MaterialThemePreview {
        Surface {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                IntermittentChoiceButton(
                    text = "Multiple Unselected",
                    allowMultiple = true,
                ) {
                }

                IntermittentChoiceButton(
                    text = "Multiple Selected",
                    allowMultiple = true,
                    selected = true,
                ) {
                }

                IntermittentChoiceButton(
                    text = "Single Unselected",
                    allowMultiple = false,
                ) {
                }

                IntermittentChoiceButton(
                    text = "Single Selected",
                    allowMultiple = false,
                    selected = true,
                ) {
                }
            }
        }
    }
}
