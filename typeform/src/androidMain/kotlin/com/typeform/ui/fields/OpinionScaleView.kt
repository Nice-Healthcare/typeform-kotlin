package com.typeform.ui.fields

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.typeform.models.ResponseValue
import com.typeform.schema.OpinionScale
import com.typeform.schema.Validations
import com.typeform.ui.components.IntermittentChoiceButton
import com.typeform.ui.components.StyledTextView
import com.typeform.ui.models.ResponseState
import com.typeform.ui.models.Settings

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun OpinionScaleView(
    settings: Settings,
    properties: OpinionScale,
    responseState: ResponseState,
    validations: Validations?,
    stateHandler: (ResponseState) -> Unit,
) {
    var selected: Int? by remember { mutableStateOf(responseState.response?.asInt()) }

    val range = IntRange(
        if (properties.start_at_one) 1 else 0,
        if (properties.start_at_one) properties.steps else properties.steps - 1
    )

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

    fun select(value: Int) {
        selected = if (value == selected) null else value

        updateState()
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(settings.presentation.descriptionContentVerticalSpacing),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            StyledTextView(
                text = properties.labels.left,
                textStyle = MaterialTheme.typography.caption,
            )

            StyledTextView(
                text = properties.labels.right,
                textStyle = MaterialTheme.typography.caption,
            )
        }

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(settings.presentation.contentHorizontalSpacing),
            maxItemsInEachRow = 5
        ) {
            range.forEach { step ->
                IntermittentChoiceButton(
                    settings = settings,
                    text = "$step",
                    modifier = Modifier.weight(1f),
                    allowMultiple = null,
                    selected = selected == step,
                ) {
                    select(step)
                }
            }
        }
    }

    updateState()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun OpinionScaleViewPreview() {
    OpinionScaleView(
        settings = Settings(),
        properties = OpinionScale(
            steps = 11,
            labels = OpinionScale.Labels("leading", "trailing"),
            start_at_one = false,
        ),
        responseState = ResponseState(),
        validations = null,
    ) {
    }
}
