package com.typeform.ui.fields

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.typeform.models.Response
import com.typeform.models.ResponseValue
import com.typeform.models.Responses
import com.typeform.schema.DateStamp
import com.typeform.schema.Validations
import com.typeform.ui.components.StyledTextView
import com.typeform.ui.models.Settings
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DateView(
    settings: Settings,
    ref: String,
    properties: DateStamp,
    responses: Responses,
    responseHandler: Response,
    validations: Validations?,
    validationHandler: ((Boolean) -> Unit)?,
) {
    val pickerState = rememberDatePickerState((responses[ref]?.asDate() ?: Date()).time)
    val isOptional = validations?.required != true
    val isNotSure = remember { mutableStateOf(false) }

    fun determineValidity() {
        if (validationHandler == null) {
            return
        }

        if (validations == null || !validations.required) {
            validationHandler(true)
            return
        }

        val date = if (pickerState.selectedDateMillis != null) Date(pickerState.selectedDateMillis!!) else null

        validationHandler(date != null)
    }

    fun select(milliseconds: Long?) {
        if (milliseconds != null) {
            responseHandler(ref, ResponseValue.DateValue(Date(milliseconds)))
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                StyledTextView(
                    text = settings.localization.nullDate,
                    textStyle = MaterialTheme.typography.caption,
                )

                if (isOptional) {
                    Switch(
                        checked = isNotSure.value,
                        onCheckedChange = {
                            if (isNotSure.value) {
                                select(null)
                            } else {
                                select(Date().time)
                            }

                            isNotSure.value = !isNotSure.value
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = MaterialTheme.colors.primary,
                            uncheckedThumbColor = MaterialTheme.colors.onBackground,
                        ),
                    )
                }
            }

            if (!isOptional || !isNotSure.value) {
                DatePicker(
                    state = pickerState,
                    dateValidator = { milliseconds ->
                        select(milliseconds)
                        true
                    },
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

    determineValidity()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DateViewPreview() {
    DateView(
        settings = Settings(),
        ref = "",
        properties = DateStamp(
            separator = "",
            structure = "",
            description = null,
        ),
        responses = mutableMapOf(),
        responseHandler = { _, _ -> },
        validations = null,
        validationHandler = null,
    )
}
