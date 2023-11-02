package com.typeform.ui.fields

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.typeform.models.Response
import com.typeform.models.ResponseValue
import com.typeform.models.Responses
import com.typeform.schema.Choice
import com.typeform.schema.MultipleChoice
import com.typeform.schema.Validations
import com.typeform.ui.components.ChoiceButtonView
import com.typeform.ui.components.StyledTextView
import com.typeform.ui.models.Settings

@Composable
internal fun MultipleChoiceView(
    settings: Settings,
    ref: String,
    properties: MultipleChoice,
    responses: Responses,
    responseHandler: Response,
    validations: Validations?,
    validationHandler: ((Boolean) -> Unit)?,
) {
    val selected: MutableState<List<Choice>> = remember { mutableStateOf(responses[ref]?.asChoices() ?: emptyList()) }

    val choices = properties.orderedChoices()
    val allowMultiple = properties.allow_multiple_selection

    fun determineValidity() {
        if (validationHandler == null) {
            return
        }

        if (validations == null || !validations.required) {
            validationHandler(true)
            return
        }

        validationHandler(selected.value.isNotEmpty())
    }

    fun toggle(choice: Choice) {
        val selections = selected.value.toMutableList()

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

        selected.value = selections

        if (selections.isEmpty()) {
            responseHandler(ref, null)
        } else {
            if (allowMultiple) {
                responseHandler(ref, ResponseValue.ChoicesValue(selections))
            } else {
                responseHandler(ref, ResponseValue.ChoiceValue(selections.first()))
            }
        }

        determineValidity()
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(settings.presentation.descriptionContentVerticalSpacing),
    ) {
        properties.description?.let {
            StyledTextView(
                text = it,
                textStyle = MaterialTheme.typography.caption,
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(settings.presentation.contentVerticalSpacing),
        ) {
            choices.forEach { choice ->
                ChoiceButtonView(
                    settings = settings,
                    text = choice.label,
                    allowMultiple = allowMultiple,
                    selected = selected.value.contains(choice),
                ) {
                    toggle(choice)
                }
            }
        }
    }

    determineValidity()
}

@Preview(showBackground = true)
@Composable
private fun MultipleChoiceViewPreview(
    @PreviewParameter(MultipleChoiceParameterProvider::class) reference: String,
) {
    MultipleChoiceView(
        settings = Settings(),
        ref = reference,
        properties = MultipleChoice(
            choices = emptyList(),
            randomize = false,
            allow_multiple_selection = false,
            allow_other_choice = false,
            vertical_alignment = true,
            description = null,
        ),
        responses = mutableMapOf(),
        responseHandler = { _, _ -> },
        validations = null,
        validationHandler = null,
    )
}

private class MultipleChoiceParameterProvider : PreviewParameterProvider<String> {
    override val values: Sequence<String>
        get() = sequenceOf(
            "",
            "",
        )
}
