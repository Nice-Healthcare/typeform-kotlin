package com.typeform.ui.fields

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.typeform.models.ResponseValue
import com.typeform.schema.Validations
import com.typeform.schema.questions.Number
import com.typeform.ui.models.ResponseState
import com.typeform.ui.models.Settings

@Composable
internal fun NumberView(
    settings: Settings,
    properties: Number,
    responseState: ResponseState,
    validations: Validations?,
    stateHandler: (ResponseState) -> Unit,
) {
    var selected: Int? by remember { mutableStateOf(responseState.response?.asInt()) }

    fun updateState() {
        var state = responseState

        val selection = selected
        state = if (selection != null) {
            state.copy(response = ResponseValue.IntValue(selection))
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

    fun select(value: Int?) {
        selected = value

        updateState()
    }

    LaunchedEffect(Unit) {
        updateState()
    }

    OutlinedTextField(
        value = "${selected ?: ""}",
        onValueChange = { value ->
            select(value.toIntOrNull())
        },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
private fun NumberViewPreview() {
    NumberView(
        settings = Settings(),
        properties = Number(
            description = null,
        ),
        responseState = ResponseState(),
        validations = null,
    ) {
    }
}
