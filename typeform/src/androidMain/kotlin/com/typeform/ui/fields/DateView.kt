package com.typeform.ui.fields

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.typeform.models.ResponseValue
import com.typeform.schema.questions.DateStamp
import com.typeform.schema.structure.Validations
import com.typeform.ui.LocalLocalization
import com.typeform.ui.LocalPresentation
import com.typeform.ui.components.StyledTextView
import com.typeform.ui.models.ResponseState
import com.typeform.ui.preview.MaterialThemePreview
import java.util.Date

@Composable
internal fun DateView(
    properties: DateStamp,
    responseState: ResponseState,
    validations: Validations?,
    stateHandler: (ResponseState) -> Unit,
) {
    val pickerState = rememberDatePickerState((responseState.response?.asDate() ?: Date()).time)
    var milliseconds by remember { mutableStateOf(responseState.response?.asDate()?.time) }
    var isNotSure by remember { mutableStateOf(false) }

    val isOptional = validations?.required != true

    fun updateState() {
        var state = responseState

        val millis = milliseconds
        state = if (millis != null && !isNotSure) {
            state.copy(response = ResponseValue.DateValue(Date(millis)))
        } else {
            state.copy(response = null)
        }

        state = if (validations != null && validations.required) {
            state.copy(invalid = state.response == null)
        } else {
            state.copy(invalid = false)
        }

        stateHandler(state)
    }

    fun select(millis: Long?) {
        milliseconds = millis

        updateState()
    }

    LaunchedEffect(Unit) {
        updateState()
    }

    DisposableEffect(pickerState.selectedDateMillis) {
        select(pickerState.selectedDateMillis)
        onDispose { }
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

        if (isOptional) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                StyledTextView(
                    text = LocalLocalization.current.nullDate,
                    textStyle = MaterialTheme.typography.bodyMedium,
                )

                Switch(
                    checked = isNotSure,
                    onCheckedChange = {
                        isNotSure = !isNotSure
                        if (isNotSure) {
                            select(null)
                        } else {
                            select(pickerState.selectedDateMillis ?: Date().time)
                        }
                    },
                )
            }
        }

        if (!isOptional || !isNotSure) {
            DatePicker(
                state = pickerState,
                title = null,
                headline = null,
                showModeToggle = false,
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun DateViewPreview() {
    MaterialThemePreview {
        DateView(
            properties = DateStamp(
                separator = "",
                structure = "",
                description = null,
            ),
            responseState = ResponseState(),
            validations = null,
        ) {
        }
    }
}
