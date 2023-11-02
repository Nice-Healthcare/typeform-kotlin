package com.typeform.ui.fields

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.typeform.models.Response
import com.typeform.models.ResponseValue
import com.typeform.models.Responses
import com.typeform.schema.Number
import com.typeform.schema.Validations
import com.typeform.ui.components.StyledTextView
import com.typeform.ui.models.Settings

@Composable
internal fun NumberView(
    settings: Settings,
    ref: String,
    properties: Number,
    responses: Responses,
    responseHandler: Response,
    validations: Validations?,
    validationHandler: ((Boolean) -> Unit)?,
) {
    val selected: MutableState<Int?> = remember { mutableStateOf(responses[ref]?.asInt()) }

    fun determineValidity() {
        if (validationHandler == null) {
            return
        }

        if (validations == null || !validations.required) {
            validationHandler(true)
            return
        }

        validationHandler(selected.value != null)
    }

    fun select(value: Int?) {
        selected.value = value

        if (value != null) {
            responseHandler(ref, ResponseValue.IntValue(value))
        } else {
            responseHandler(ref, null)
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

        OutlinedTextField(
            value = "${selected.value ?: ""}",
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

    determineValidity()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun NumberViewPreview() {
    NumberView(
        settings = Settings(),
        ref = "",
        properties = Number(
            description = null,
        ),
        responses = mutableMapOf(),
        responseHandler = { _, _ -> },
        validations = null,
        validationHandler = null,
    )
}
