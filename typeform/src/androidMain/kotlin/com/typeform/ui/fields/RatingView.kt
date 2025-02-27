package com.typeform.ui.fields

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Cloud
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.typeform.models.ResponseValue
import com.typeform.schema.Rating
import com.typeform.schema.Validations
import com.typeform.ui.components.StyledTextView
import com.typeform.ui.models.ResponseState
import com.typeform.ui.models.Settings
import com.typeform.ui.preview.ThemePreview

@Composable
internal fun RatingView(
    settings: Settings,
    properties: Rating,
    responseState: ResponseState,
    validations: Validations?,
    stateHandler: (ResponseState) -> Unit,
) {
    var selected: Int? by remember { mutableStateOf(responseState.response?.asInt()) }

    val range = IntRange(1, properties.steps)
    val outlinedImage = when (properties.shape.lowercase()) {
        "cloud" -> {
            Icons.Outlined.Cloud
        }
        else -> {
            Icons.Outlined.Star
        }
    }
    val filledImage = when (properties.shape.lowercase()) {
        "cloud" -> {
            Icons.Filled.Cloud
        }
        else -> {
            Icons.Filled.Star
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

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        range.forEach { step ->
            val filled = (selected ?: 0) >= step
            val tint = if (filled) {
                settings.rating.colors.contentColor(enabled = true)
            } else {
                settings.rating.colors.backgroundColor(enabled = true)
            }

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
                        tint = tint.value,
                    )

                    StyledTextView(
                        text = "$step",
                        textStyle = MaterialTheme.typography.body1,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RatingViewPreview() {
    ThemePreview {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            RatingView(
                settings = Settings(),
                properties = Rating(
                    shape = "star",
                    steps = 5,
                    description = null,
                ),
                responseState = ResponseState(),
                validations = null,
            ) {
            }

            RatingView(
                settings = Settings(),
                properties = Rating(
                    shape = "star",
                    steps = 5,
                    description = null,
                ),
                responseState = ResponseState(),
                validations = null,
            ) {
            }
        }
    }
}
