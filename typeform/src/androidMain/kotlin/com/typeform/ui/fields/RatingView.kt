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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.typeform.models.Response
import com.typeform.models.ResponseValue
import com.typeform.models.Responses
import com.typeform.schema.Rating
import com.typeform.schema.Validations
import com.typeform.ui.components.StyledTextView
import com.typeform.ui.models.Settings

@Composable
internal fun RatingView(
    settings: Settings,
    ref: String,
    properties: Rating,
    responses: Responses,
    responseHandler: Response,
    validations: Validations?,
    validationHandler: ((Boolean) -> Unit)?,
) {
    val selected: MutableState<Int?> = remember { mutableStateOf(responses[ref]?.asInt()) }

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

    fun select(value: Int) {
        var selectedValue = selected.value

        selectedValue = if (selectedValue == value) null else value

        selected.value = selectedValue

        if (selectedValue != null) {
            responseHandler(ref, ResponseValue.IntValue(selectedValue))
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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            range.forEach { step ->
                val filled = (selected.value ?: 0) >= step

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
                            tint = if (filled) settings.rating.selectedForegroundColor else settings.rating.unselectedForegroundColor,
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

    determineValidity()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RatingViewPreview() {
    RatingView(
        settings = Settings(),
        ref = "",
        properties = Rating(
            shape = "star",
            steps = 5,
            description = null,
        ),
        responses = mutableMapOf(),
        responseHandler = { _, _ -> },
        validations = null,
        validationHandler = null,
    )
}
