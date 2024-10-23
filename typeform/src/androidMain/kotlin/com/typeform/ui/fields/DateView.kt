package com.typeform.ui.fields

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.tooling.preview.Preview
import com.typeform.models.ResponseValue
import com.typeform.schema.DateStamp
import com.typeform.schema.Validations
import com.typeform.ui.components.StyledTextView
import com.typeform.ui.models.ResponseState
import com.typeform.ui.models.Settings
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DateView(
    settings: Settings,
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
            if (isOptional) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    StyledTextView(
                        text = settings.localization.nullDate,
                        textStyle = MaterialTheme.typography.caption,
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
                        colors = settings.switch.colors,
                    )
                }
            }

            if (!isOptional || !isNotSure) {
                DatePicker(
                    state = pickerState,
                    title = null,
                    headline = null,
                    showModeToggle = false,
                    colors = DatePickerDefaults.colors(
                        weekdayContentColor = settings.calendar.weekdayContentColor,
                        dayContentColor = settings.calendar.dayContentColor,
                        selectedDayContentColor = settings.calendar.selectedDayContentColor,
                        selectedDayContainerColor = settings.calendar.selectedDayContainerColor,
                        todayContentColor = settings.calendar.todayDateContentColor,
                        todayDateBorderColor = settings.calendar.todayDateContainerColor,
                    ),
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DateViewPreview() {
    DateView(
        settings = Settings(),
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
