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
import androidx.compose.ui.Alignment
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

    val start = if (properties.start_at_one) 1 else 0
    val end = if (properties.start_at_one) properties.steps else properties.steps - 1
    val range = IntRange(start, end)
    val leadingLabel = String.format("%d: %s", start, properties.labels.left)
    val trailingLabel = String.format("%d: %s", end, properties.labels.right)

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
                text = leadingLabel,
                textStyle = MaterialTheme.typography.caption,
            )

            StyledTextView(
                text = trailingLabel,
                textStyle = MaterialTheme.typography.caption,
            )
        }

        FlowRow(
            modifier = if (settings.rating.fillMaxWidth) Modifier else Modifier.align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.spacedBy(settings.presentation.contentHorizontalSpacing),
            verticalArrangement = Arrangement.spacedBy(settings.presentation.contentVerticalSpacing),
            maxItemsInEachRow = 6
        ) {
            range.forEach { step ->
                IntermittentChoiceButton(
                    settings = settings,
                    text = "$step",
                    modifier = if (settings.rating.fillMaxWidth) Modifier.weight(1f) else Modifier,
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun OpinionScaleViewPreviewFillMaxWidth() {
    OpinionScaleView(
        settings = Settings(
            rating = Settings.Rating(
                fillMaxWidth = true,
            )
        ),
        properties = OpinionScale(
            steps = 10,
            labels = OpinionScale.Labels("leading", "trailing"),
            start_at_one = true,
        ),
        responseState = ResponseState(),
        validations = null,
    ) {
    }
}
