package com.typeform.ui.fields

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.typeform.models.ResponseValue
import com.typeform.schema.Validations
import com.typeform.schema.questions.ShortText
import com.typeform.ui.models.ResponseState
import com.typeform.ui.models.Settings

@Composable
internal fun ShortTextView(
    settings: Settings,
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

    OutlinedTextField(
        value = selected,
        onValueChange = { value ->
            select(value)
        },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = MaterialTheme.typography.body1.color,
            backgroundColor = MaterialTheme.colors.background,
            focusedBorderColor = MaterialTheme.colors.primary,
        ),
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ShortTextViewPreview() {
    ShortTextView(
        settings = Settings(),
        properties = ShortText(
            description = null,
        ),
        responseState = ResponseState(),
        validations = null,
    ) {
    }
}
