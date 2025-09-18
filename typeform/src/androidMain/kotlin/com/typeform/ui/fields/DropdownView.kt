package com.typeform.ui.fields

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import com.typeform.models.ResponseValue
import com.typeform.schema.Choice
import com.typeform.schema.Validations
import com.typeform.schema.questions.Dropdown
import com.typeform.ui.models.ResponseState
import com.typeform.ui.models.Settings

@Composable
internal fun DropdownView(
    settings: Settings,
    properties: Dropdown,
    responseState: ResponseState,
    validations: Validations?,
    stateHandler: (ResponseState) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    var selected by remember { mutableStateOf(responseState.response?.asChoice()) }
    var expanded by remember { mutableStateOf(false) }
    var focused by remember { mutableStateOf(false) }

    val choices = properties.orderedChoices()

    fun updateState() {
        var state = responseState

        val selection = selected
        state = if (selection != null) {
            state.copy(response = ResponseValue.ChoiceValue(selection))
        } else {
            state.copy(response = null)
        }

        state = if (validations != null && validations.required) {
            state.copy(invalid = selection == null)
        } else {
            state.copy(invalid = false)
        }

        stateHandler(state)
    }

    fun select(choice: Choice?) {
        selected = choice
        expanded = false
        focusManager.clearFocus()

        updateState()
    }

    Column {
        OutlinedTextField(
            value = selected?.label ?: settings.localization.emptyChoice,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged {
                    focused = it.isFocused
                    expanded = it.isFocused
                },
            readOnly = true,
            trailingIcon = {
                IconButton(
                    onClick = {
                        expanded = true
                        focusRequester.requestFocus()
                    },
                ) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = settings.localization.emptyChoice,
                    )
                }
            },
            shape = MaterialTheme.shapes.small,
            colors = settings.field.colors,
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                focusManager.clearFocus()
            },
        ) {
            DropdownMenuItem(
                onClick = {
                    select(null)
                },
            ) {
                DropdownChoiceRow(
                    settings = settings,
                    label = settings.localization.emptyChoice,
                    selected = selected == null,
                )
            }

            choices.forEach { choice ->
                DropdownMenuItem(
                    onClick = {
                        select(choice)
                    },
                ) {
                    DropdownChoiceRow(
                        settings = settings,
                        label = choice.label,
                        selected = selected == choice,
                    )
                }
            }
        }
    }

    updateState()
}

@Composable
private fun DropdownChoiceRow(
    settings: Settings,
    label: String,
    selected: Boolean,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(settings.presentation.contentHorizontalSpacing),
    ) {
        Text(
            text = label,
            color = MaterialTheme.typography.body1.color,
            style = MaterialTheme.typography.body1,
        )

        if (selected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = MaterialTheme.typography.button.color,
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DropdownViewPreview() {
    DropdownView(
        settings = Settings(),
        properties = Dropdown(
            choices = emptyList(),
            description = null,
            randomize = false,
            alphabetical_order = false,
        ),
        responseState = ResponseState(),
        validations = null,
    ) {
    }
}
