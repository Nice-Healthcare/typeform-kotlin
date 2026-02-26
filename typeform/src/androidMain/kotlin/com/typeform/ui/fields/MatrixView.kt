package com.typeform.ui.fields

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.typeform.models.ResponseValue
import com.typeform.schema.structure.Choice
import com.typeform.schema.structure.Field
import com.typeform.schema.structure.Matrix
import com.typeform.schema.structure.Validations
import com.typeform.ui.LocalPresentation
import com.typeform.ui.components.IntermittentChoiceButton
import com.typeform.ui.components.StyledTextView
import com.typeform.ui.models.ResponseState
import com.typeform.ui.preview.MaterialThemePreview
import com.typeform.ui.preview.previewMatrix1
import com.typeform.ui.preview.previewMatrix2
import com.typeform.ui.preview.previewMatrix3

/**
 * Note... attempted to use LazyVerticalGrid but that causes a layout constraint issue
 * due to being wrapped in a scrolling view.
 */
@Composable
internal fun MatrixView(
    properties: Matrix,
    responseState: ResponseState,
    validations: Validations?,
    stateHandler: (ResponseState) -> Unit,
) {
    val presentation = LocalPresentation.current
    var selected: Map<String, List<Choice>> by remember { mutableStateOf(responseState.response?.asChoicesByReference() ?: emptyMap()) }

    fun updateState() {
        var state = responseState

        state = if (selected.values.any { it.isNotEmpty() }) {
            state.copy(
                response = ResponseValue.ChoicesByReferenceValue(selected),
            )
        } else {
            state.copy(
                response = null,
            )
        }

        state = if (validations != null && validations.required) {
            state.copy(
                invalid = if (properties.allow_multiple_selections) {
                    selected.values.any { it.isEmpty() }
                } else {
                    selected.values.any { it.count() != 1 }
                },
            )
        } else {
            state.copy(
                invalid = false,
            )
        }

        stateHandler(state)
    }

    fun toggleChoice(
        choice: Choice,
        iteration: Matrix.Iteration,
    ) {
        val selections = selected.toMutableMap()
        var choices = (selections[iteration.ref] ?: emptyList()).toMutableList()
        if (properties.allow_multiple_selections) {
            val index = choices.indexOf(choice)
            if (index != -1) {
                choices.removeAt(index)
            } else {
                choices.add(choice)
            }
        } else {
            choices = mutableListOf(choice)
        }
        selections[iteration.ref] = choices
        selected = selections

        updateState()
    }

    LaunchedEffect(Unit) {
        updateState()
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(presentation.contentVerticalSpacing),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(presentation.contentHorizontalSpacing),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(
                modifier = Modifier.weight(1f),
            )

            properties.columns.forEach {
                StyledTextView(
                    text = it,
                    textStyle = MaterialTheme.typography.body1,
                    modifier = Modifier.width(65.dp),
                    textAlign = TextAlign.Center,
                )
            }
        }

        properties.questions.forEach { iteration ->
            HorizontalDivider()

            Row(
                horizontalArrangement = Arrangement.spacedBy(presentation.contentHorizontalSpacing),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                StyledTextView(
                    text = iteration.title,
                    textStyle = MaterialTheme.typography.body1,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End,
                )

                iteration.question.choices.forEach { choice ->
                    IntermittentChoiceButton(
                        text = "",
                        modifier = Modifier.width(65.dp),
                        allowMultiple = properties.allow_multiple_selections,
                        selected = selected[iteration.ref]?.contains(choice) ?: false,
                    ) {
                        toggleChoice(choice, iteration)
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun MatrixViewPreview() {
    MaterialThemePreview {
        MatrixView(
            properties = Matrix(
                fields = listOf(
                    Field.previewMatrix1,
                    Field.previewMatrix2,
                    Field.previewMatrix3,
                ),
            ),
            responseState = ResponseState(),
            validations = null,
        ) {
        }
    }
}
