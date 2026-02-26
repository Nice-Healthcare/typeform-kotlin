package com.typeform.ui.fields

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.typeform.models.ResponseValue
import com.typeform.resources.Res
import com.typeform.resources.cloud_24dp
import com.typeform.resources.cloud_fill_24dp
import com.typeform.resources.star_24dp
import com.typeform.resources.star_fill_24dp
import com.typeform.schema.questions.Rating
import com.typeform.schema.structure.Validations
import com.typeform.ui.components.ContentContainerView
import com.typeform.ui.components.TextView
import com.typeform.ui.models.Appearance
import com.typeform.ui.models.ResponseState
import com.typeform.ui.preview.TypeformPreview
import org.jetbrains.compose.resources.vectorResource

@Composable
internal fun RatingView(
    properties: Rating,
    responseState: ResponseState,
    validations: Validations?,
    stateHandler: (ResponseState) -> Unit,
) {
    var selected: Int? by remember { mutableStateOf(responseState.response?.asInt()) }

    val range = IntRange(1, properties.steps)
    val outlinedImage = when (properties.shape.lowercase()) {
        "cloud" -> {
            vectorResource(Res.drawable.cloud_24dp)
        }
        else -> {
            vectorResource(Res.drawable.star_24dp)
        }
    }
    val filledImage = when (properties.shape.lowercase()) {
        "cloud" -> {
            vectorResource(Res.drawable.cloud_fill_24dp)
        }
        else -> {
            vectorResource(Res.drawable.star_fill_24dp)
        }
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

    fun select(value: Int) {
        selected = if (value == selected) null else value

        updateState()
    }

    LaunchedEffect(Unit) {
        updateState()
    }

    ContentContainerView(
        description = properties.description,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            range.forEach { step ->
                val filled = (selected ?: 0) >= step

                IconButton(
                    onClick = {
                        select(step)
                    },
                    modifier = Modifier.weight(1f),
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Icon(
                            imageVector = if (filled) filledImage else outlinedImage,
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth(),
                        )

                        TextView(
                            text = step.toString(),
                            typeStyle = Appearance.TypeStyle.LABEL,
                        )
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun RatingViewPreview() {
    TypeformPreview(
        headline = "How would you rate the experience so far?",
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            RatingView(
                properties = Rating(
                    shape = "star",
                    steps = 5,
                    description = "These ones are stars.",
                ),
                responseState = ResponseState(),
                validations = null,
            ) {
            }

            RatingView(
                properties = Rating(
                    shape = "cloud",
                    steps = 5,
                    description = "These ones are clouds.",
                ),
                responseState = ResponseState(),
                validations = null,
            ) {
            }
        }
    }
}
