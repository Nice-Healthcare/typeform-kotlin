package com.typeform.ui.fields

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.typeform.models.Response
import com.typeform.models.ResponseValue
import com.typeform.models.Responses
import com.typeform.schema.Validations
import com.typeform.schema.YesNo
import com.typeform.ui.components.ChoiceButtonView
import com.typeform.ui.components.StyledTextView
import com.typeform.ui.models.Settings

@Composable
internal fun YesNoView(
    settings: Settings,
    ref: String,
    properties: YesNo,
    responses: Responses,
    responseHandler: Response,
    validations: Validations?,
    validationHandler: ((Boolean) -> Unit)?,
) {
    val selected: MutableState<Boolean?> = remember { mutableStateOf(responses[ref]?.asBoolean()) }

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

    fun toggle(value: Boolean) {
        var isSelected = selected.value

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

        selected.value = isSelected

        if (isSelected != null) {
            responseHandler(ref, ResponseValue.BooleanValue(isSelected))
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

        Column(
            verticalArrangement = Arrangement.spacedBy(settings.presentation.contentVerticalSpacing),
        ) {
            ChoiceButtonView(
                settings = settings,
                text = settings.localization.yes,
                selected = selected.value == true,
            ) {
                toggle(true)
            }

            ChoiceButtonView(
                settings = settings,
                text = settings.localization.no,
                selected = selected.value == false,
            ) {
                toggle(false)
            }
        }
    }

    determineValidity()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun YesNoViewPreview() {
    YesNoView(
        settings = Settings(),
        ref = "",
        properties = YesNo(
            description = null,
        ),
        responses = mutableMapOf(),
        responseHandler = { _, _ -> },
        validations = null,
        validationHandler = null,
    )
}
