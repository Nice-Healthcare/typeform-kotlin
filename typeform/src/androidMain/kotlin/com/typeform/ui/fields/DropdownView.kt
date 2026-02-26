package com.typeform.ui.fields

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.typeform.models.ResponseValue
import com.typeform.resources.Res
import com.typeform.resources.check_24dp
import com.typeform.resources.keyboard_arrow_down_24dp
import com.typeform.resources.keyboard_arrow_up_24dp
import com.typeform.schema.questions.Dropdown
import com.typeform.schema.structure.Choice
import com.typeform.schema.structure.Validations
import com.typeform.ui.LocalLocalization
import com.typeform.ui.components.ContentContainerView
import com.typeform.ui.components.TextView
import com.typeform.ui.models.Appearance
import com.typeform.ui.models.ResponseState
import com.typeform.ui.preview.TypeformPreview
import org.jetbrains.compose.resources.vectorResource

@Composable
internal fun DropdownView(
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

    ContentContainerView(
        description = properties.description,
    ) {
        OutlinedTextField(
            value = selected?.label ?: LocalLocalization.current.emptyChoice,
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
                        imageVector = if (expanded) {
                            vectorResource(
                                Res.drawable.keyboard_arrow_up_24dp,
                            )
                        } else {
                            vectorResource(Res.drawable.keyboard_arrow_down_24dp)
                        },
                        contentDescription = LocalLocalization.current.emptyChoice,
                    )
                }
            },
            shape = MaterialTheme.shapes.small,
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                focusManager.clearFocus()
            },
        ) {
            DropdownMenuItem(
                text = {
                    TextView(
                        text = LocalLocalization.current.emptyChoice,
                        typeStyle = Appearance.TypeStyle.TITLE,
                    )
                },
                onClick = {
                    select(null)
                },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    if (selected == null) {
                        Icon(
                            imageVector = vectorResource(Res.drawable.check_24dp),
                            contentDescription = null,
                        )
                    }
                },
            )

            choices.forEach { choice ->
                DropdownMenuItem(
                    text = {
                        TextView(
                            text = choice.label,
                            typeStyle = Appearance.TypeStyle.TITLE,
                        )
                    },
                    onClick = {
                        select(choice)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        if (selected == choice) {
                            Icon(
                                imageVector = vectorResource(Res.drawable.check_24dp),
                                contentDescription = null,
                            )
                        }
                    },
                )
            }
        }
    }

    updateState()
}

@PreviewLightDark
@Composable
private fun DropdownViewPreview() {
    TypeformPreview(
        headline = "What is your quest?",
    ) {
        DropdownView(
            properties = Dropdown(
                choices = emptyList(),
                description = "If you don't know, you might be in the wrong place.",
                randomize = false,
                alphabetical_order = false,
            ),
            responseState = ResponseState(),
            validations = null,
        ) {
        }
    }
}
