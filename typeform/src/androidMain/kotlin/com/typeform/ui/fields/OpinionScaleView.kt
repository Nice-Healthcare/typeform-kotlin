package com.typeform.ui.fields

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeGesturesPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.typeform.models.ResponseValue
import com.typeform.schema.questions.OpinionScale
import com.typeform.schema.structure.Validations
import com.typeform.ui.LocalLocalization
import com.typeform.ui.components.ContentContainerView
import com.typeform.ui.components.TextView
import com.typeform.ui.models.Appearance
import com.typeform.ui.models.ResponseState
import com.typeform.ui.preview.TypeformPreview
import java.util.Locale

@Composable
internal fun OpinionScaleView(
    properties: OpinionScale,
    responseState: ResponseState,
    validations: Validations?,
    stateHandler: (ResponseState) -> Unit,
) {
    val start = if (properties.start_at_one) 1 else 0
    val end = if (properties.start_at_one) properties.steps else (properties.steps - 1)
    val steps = properties.steps - 1
    val step = 1f / end.toFloat()

    var selected: Int? by remember { mutableStateOf(responseState.response?.asInt()) }
    var value: Float by remember {
        mutableFloatStateOf(
            if (responseState.response?.asInt() != null) {
                responseState.response.asInt()!!.toFloat() * step
            } else {
                0f
            },
        )
    }

    val leadingLabel = if (properties.labels != null) {
        String.format(
            Locale.getDefault(),
            "%d: %s",
            start,
            properties.labels.left,
        )
    } else {
        String.format(
            Locale.getDefault(),
            "%d",
            start,
        )
    }
    val trailingLabel = if (properties.labels != null) {
        String.format(
            Locale.getDefault(),
            "%d: %s",
            end,
            properties.labels.right,
        )
    } else {
        String.format(
            Locale.getDefault(),
            "%d",
            end,
        )
    }

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

    fun selectFloat(newValue: Float) {
        selected = if (newValue <= 0f) {
            null
        } else if (newValue >= 1f) {
            end
        } else {
            (newValue / step).toInt()
        }

        value = newValue

        updateState()
    }

    LaunchedEffect(Unit) {
        updateState()
    }

    ContentContainerView(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextView(
            text = if (selected == null) LocalLocalization.current.emptyChoice else "$selected",
            typeStyle = Appearance.TypeStyle.TITLE,
        )

        // Additional padding added here to account for the system gesture insets.
        // The system has priority over gestures within a certain margin of the device edge.
        Slider(
            value = value,
            onValueChange = {
                selectFloat(it)
            },
            modifier = Modifier.safeGesturesPadding(),
            steps = steps,
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            TextView(
                modifier = Modifier.width(120.dp),
                text = leadingLabel,
                typeStyle = Appearance.TypeStyle.LABEL,
            )

            TextView(
                modifier = Modifier.width(120.dp),
                text = trailingLabel,
                typeStyle = Appearance.TypeStyle.LABEL,
                textAlign = TextAlign.End,
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun OpinionScaleViewPreview() {
    TypeformPreview(
        headline = "Great example of using native controls.",
    ) {
        Column {
            OpinionScaleView(
                properties = OpinionScale(
                    steps = 11,
                    labels = OpinionScale.Labels(
                        left = "no pain",
                        right = "worst pain imaginable",
                    ),
                    start_at_one = false,
                ),
                responseState = ResponseState(),
                validations = null,
            ) {
            }

            OpinionScaleView(
                properties = OpinionScale(
                    steps = 5,
                    labels = OpinionScale.Labels(
                        left = "Not confident",
                        right = "Very confident",
                    ),
                    start_at_one = true,
                ),
                responseState = ResponseState(),
                validations = null,
            ) {
            }
        }
    }
}
