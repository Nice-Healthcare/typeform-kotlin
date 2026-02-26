package com.typeform.ui.fields

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.typeform.models.ResponseValue
import com.typeform.schema.questions.ShortText
import com.typeform.schema.structure.Validations
import com.typeform.ui.LocalPresentation
import com.typeform.ui.components.StyledTextView
import com.typeform.ui.models.ResponseState
import com.typeform.ui.preview.MaterialThemePreview

@Composable
internal fun ShortTextView(
    properties: ShortText,
    responseState: ResponseState,
    validations: Validations?,
    stateHandler: (ResponseState) -> Unit,
) {
    var selected: String by remember { mutableStateOf(responseState.response?.asString() ?: "") }

    fun updateState() {
        var state = responseState

        state = if (selected.isNotEmpty()) {
            state.copy(response = ResponseValue.StringValue(selected))
        } else {
            state.copy(response = null)
        }

        state = if (validations != null && validations.required) {
            state.copy(invalid = selected.isEmpty())
        } else {
            state.copy(invalid = false)
        }

        stateHandler(state)
    }

    fun select(value: String) {
        selected = value

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

        OutlinedTextField(
            value = selected,
            onValueChange = { value ->
                select(value)
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
            },
            singleLine = true,
        )
    }
}

@PreviewLightDark
@Composable
private fun ShortTextViewPreview() {
    MaterialThemePreview {
        ShortTextView(
            properties = ShortText(
                description = null,
            ),
            responseState = ResponseState(),
            validations = null,
        ) {
        }
    }
}
