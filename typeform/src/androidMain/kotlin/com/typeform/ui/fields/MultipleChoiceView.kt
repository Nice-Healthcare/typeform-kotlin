package com.typeform.ui.fields

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.typeform.models.ResponseValue
import com.typeform.schema.questions.MultipleChoice
import com.typeform.schema.structure.Choice
import com.typeform.schema.structure.Validations
import com.typeform.ui.LocalSettings
import com.typeform.ui.components.IntermittentChoiceButton
import com.typeform.ui.components.StyledTextView
import com.typeform.ui.models.LocalPresentation
import com.typeform.ui.models.ResponseState
import com.typeform.ui.preview.MaterialThemePreview
import com.typeform.ui.preview.previewCold
import com.typeform.ui.preview.previewHot

@Composable
internal fun MultipleChoiceView(
    properties: MultipleChoice,
    responseState: ResponseState,
    validations: Validations?,
    stateHandler: (ResponseState) -> Unit,
) {
    val settings = LocalSettings.current
    var selected: List<Choice> by remember { mutableStateOf(responseState.response?.asChoices() ?: emptyList()) }

    val choices = properties.orderedChoices()
    val allowMultiple = properties.allow_multiple_selection

    fun updateState() {
        var state = responseState

        state = if (selected.isEmpty()) {
            state.copy(response = null)
        } else {
            if (allowMultiple) {
                state.copy(response = ResponseValue.ChoicesValue(selected))
            } else {
                state.copy(response = ResponseValue.ChoiceValue(selected.first()))
            }
        }

        state = if (validations != null && validations.required) {
            state.copy(invalid = selected.isEmpty())
        } else {
            state.copy(invalid = false)
        }

        stateHandler(state)
    }

    fun toggle(choice: Choice) {
        val selections = selected.toMutableList()

        val index = selections.indexOf(choice)

        if (allowMultiple) {
            if (index == -1) {
                selections.add(choice)
            } else {
                selections.removeAt(index)
            }
        } else {
            selections.clear()
            if (index == -1) {
                selections.add(choice)
            }
        }

        selected = selections

        updateState()
    }

    LaunchedEffect(Unit) {
        updateState()
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(LocalPresentation.current.contentVerticalSpacing),
    ) {
        properties.description?.let {
            StyledTextView(
                text = it,
                textStyle = MaterialTheme.typography.labelMedium,
            )
        }

        choices.forEach { choice ->
            IntermittentChoiceButton(
                text = choice.label,
                allowMultiple = allowMultiple,
                selected = selected.contains(choice),
            ) {
                toggle(choice)
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun MultipleChoiceViewPreview() {
    MaterialThemePreview {
        MultipleChoiceView(
            properties = MultipleChoice(
                choices = listOf(
                    Choice.previewHot,
                    Choice.previewCold,
                ),
                randomize = false,
                allow_multiple_selection = false,
                allow_other_choice = false,
                vertical_alignment = true,
                description = null,
            ),
            responseState = ResponseState(),
            validations = null,
        ) {
        }
    }
}
