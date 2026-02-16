package com.typeform.ui.fields

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.typeform.models.ResponseValue
import com.typeform.schema.questions.YesNo
import com.typeform.schema.structure.Validations
import com.typeform.ui.LocalSettings
import com.typeform.ui.components.IntermittentChoiceButton
import com.typeform.ui.models.ResponseState

@Composable
internal fun YesNoView(
    properties: YesNo,
    responseState: ResponseState,
    validations: Validations?,
    stateHandler: (ResponseState) -> Unit,
) {
    val settings = LocalSettings.current
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

    Column(
        verticalArrangement = Arrangement.spacedBy(settings.presentation.contentVerticalSpacing),
    ) {
        IntermittentChoiceButton(
            text = settings.localization.yes,
            selected = selected == true,
        ) {
            toggle(true)
        }

        IntermittentChoiceButton(
            text = settings.localization.no,
            selected = selected == false,
        ) {
            toggle(false)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun YesNoViewPreview() {
    YesNoView(
        properties = YesNo(
            description = null,
        ),
        responseState = ResponseState(),
        validations = null,
    ) {
    }
}
