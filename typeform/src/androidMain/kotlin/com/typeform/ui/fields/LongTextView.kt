package com.typeform.ui.fields

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.typeform.models.ResponseValue
import com.typeform.schema.questions.LongText
import com.typeform.schema.structure.Validations
import com.typeform.ui.LocalColors
import com.typeform.ui.components.ContentContainerView
import com.typeform.ui.models.ResponseState
import com.typeform.ui.preview.FieldPreview

@Composable
internal fun LongTextView(
    modifier: Modifier = Modifier,
    properties: LongText,
    responseState: ResponseState,
    validations: Validations?,
    stateHandler: (ResponseState) -> Unit,
) {
    val colors = LocalColors.current

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

    ContentContainerView(
        modifier = modifier,
        description = properties.description,
    ) {
        OutlinedTextField(
            value = selected,
            onValueChange = { value ->
                select(value)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            colors = colors.outlinedTextFieldColors,
        )
    }
}

@PreviewLightDark
@Composable
private fun LongTextViewPreview() {
    FieldPreview {
        LongTextView(
            properties = LongText(
                description = "Enter something... perhaps a poem.",
            ),
            responseState = ResponseState(),
            validations = null,
        ) {
        }
    }
}
