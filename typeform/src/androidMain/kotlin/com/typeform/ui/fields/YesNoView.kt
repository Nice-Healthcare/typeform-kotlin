package com.typeform.ui.fields

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.typeform.models.ResponseValue
import com.typeform.schema.questions.YesNo
import com.typeform.schema.structure.Validations
import com.typeform.ui.LocalLocalization
import com.typeform.ui.components.ContentContainerView
import com.typeform.ui.components.IntermittentChoiceButton
import com.typeform.ui.models.ResponseState
import com.typeform.ui.preview.TypeformPreview

@Composable
internal fun YesNoView(
    properties: YesNo,
    responseState: ResponseState,
    validations: Validations?,
    stateHandler: (ResponseState) -> Unit,
) {
    var selected: Boolean? by remember { mutableStateOf(responseState.response?.asBoolean()) }

    fun updateState() {
        var state = responseState

        val selection = selected
        state = if (selection != null) {
            state.copy(response = ResponseValue.BooleanValue(selection))
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

    fun toggle(value: Boolean) {
        var isSelected = selected

        isSelected = when (isSelected) {
            true -> {
                if (value) null else false
            }

            false -> {
                if (!value) null else true
            }

            null -> {
                value
            }
        }

        selected = isSelected

        updateState()
    }

    LaunchedEffect(Unit) {
        updateState()
    }

    ContentContainerView(
        description = properties.description,
    ) {
        IntermittentChoiceButton(
            text = LocalLocalization.current.yes,
            selected = selected == true,
        ) {
            toggle(true)
        }

        IntermittentChoiceButton(
            text = LocalLocalization.current.no,
            selected = selected == false,
        ) {
            toggle(false)
        }
    }
}

@PreviewLightDark
@Composable
private fun YesNoViewPreview() {
    TypeformPreview(
        headline = "Am I Humorous?",
    ) {
        YesNoView(
            properties = YesNo(
                description = "Answer wisely, or you will be thrown from a cliff. Not really, but maybe.",
            ),
            responseState = ResponseState(),
            validations = null,
        ) {
        }
    }
}
