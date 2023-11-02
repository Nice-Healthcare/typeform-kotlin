package com.typeform.ui.fields

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.typeform.models.Response
import com.typeform.models.ResponseValue
import com.typeform.models.Responses
import com.typeform.schema.LongText
import com.typeform.schema.Validations
import com.typeform.ui.components.StyledTextView
import com.typeform.ui.models.Settings

@Composable
internal fun LongTextView(
    settings: Settings,
    ref: String,
    properties: LongText,
    responses: Responses,
    responseHandler: Response,
    validations: Validations?,
    validationHandler: ((Boolean) -> Unit)?,
) {
    val selected: MutableState<String> = remember { mutableStateOf(responses[ref]?.asString() ?: "") }

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

    fun select(value: String) {
        selected.value = value

        if (value.isNotEmpty()) {
            responseHandler(ref, ResponseValue.StringValue(value))
        } else {
            responseHandler(ref, null)
        }

        determineValidity()
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        properties.description?.let {
            StyledTextView(
                text = it,
                textStyle = MaterialTheme.typography.caption,
            )
        }

        OutlinedTextField(
            value = selected.value,
            onValueChange = { value ->
                select(value)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
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
private fun LongTextViewPreview() {
    LongTextView(
        settings = Settings(),
        ref = "",
        properties = LongText(
            description = null
        ),
        responses = mutableMapOf(),
        responseHandler = { _, _ -> },
        validations = null,
        validationHandler = null,
    )
}
