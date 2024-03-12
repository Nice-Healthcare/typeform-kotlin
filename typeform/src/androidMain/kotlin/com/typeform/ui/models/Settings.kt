package com.typeform.ui.models

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.ButtonColors
import androidx.compose.material.TextFieldColors
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Control the layout behaviors and styling of a [com.typeform.schema.Form] presentation.
 */
data class Settings(
    val localization: Localization = Localization(),
    val presentation: Presentation = Presentation(),
    val callToAction: CallToAction = CallToAction(),
    val field: Field = Field(),
    val interaction: Interaction = Interaction(),
    val calendar: Calendar = Calendar(),
    val checkbox: Checkbox = Checkbox(),
    val radio: Radio = Radio(),
    val rating: Rating = Rating(),
) {
    companion object {
    }

    data class Localization(
        val next: String = "Next",
        val cancel: String = "Cancel",
        val finish: String = "Finish",
        val yes: String = "Yes",
        val no: String = "No",
        val exit: String = "Exit",
        val abandonConfirmationTitle: String = "Abandon Form?",
        val abandonConfirmationMessage: String = "Are you sure you want to abandon the form?",
        val abandonConfirmationAction: String = "Abandon",
        val emptyChoice: String = "Select an Option",
        val nullDate: String = "I'm not sure…",
    )

    data class Presentation(
        val titleDescriptionVerticalSpacing: Dp = 30.dp,
        val descriptionContentVerticalSpacing: Dp = 30.dp,
        val topBarPadding: PaddingValues = PaddingValues(10.dp),
        val contentPadding: PaddingValues = PaddingValues(10.dp),
        val contentVerticalSpacing: Dp = 15.dp,
        val contentHorizontalSpacing: Dp = 10.dp,
        val showWelcomeImage: Boolean = true,
        val showThankYouImage: Boolean = true,
        val skipWelcomeScreen: Boolean = false,
        val skipEndingScreen: Boolean = false,
    )

    data class CallToAction(
        val backgroundColor: Color = Color.White,
        val dividerColor: Color = Color.Black,
        val colors: ButtonColors = DefaultButtonColors(),
        val containerPadding: PaddingValues = PaddingValues(10.dp),
        val contentPadding: PaddingValues = PaddingValues(10.dp),
    )

    data class Field(
        val colors: TextFieldColors = DefaultTextFieldColors(),
        val strokeWidth: Dp = 1.dp,
        val verticalPadding: Dp = 10.dp,
        val horizontalPadding: Dp = 15.dp,
    )

    data class Interaction(
        val unselectedBackgroundColor: Color = Color.Blue.copy(alpha = 0.2f),
        val unselectedStrokeColor: Color = Color.Blue.copy(alpha = 0.5f),
        val unselectedStrokeWidth: Dp = 1.dp,
        val unselectedForegroundColor: Color = Color.White,
        val selectedBackgroundColor: Color = Color.Blue.copy(alpha = 0.5f),
        val selectedStrokeColor: Color = Color.Blue.copy(alpha = 0.8f),
        val selectedStrokeWidth: Dp = 2.dp,
        val selectedForegroundColor: Color = Color.Blue,
        val padding: PaddingValues = PaddingValues(10.dp),
        val horizontalSpacing: Dp = 10.dp,
        val contentCornerRadius: Dp = 6.dp,
    )

    data class Calendar(
        val weekdayContentColor: Color = Color.Black,
        val dayContentColor: Color = Color.Black,
        val selectedDayContentColor: Color = Color.White,
        val selectedDayContainerColor: Color = Color.Blue,
        val todayDateContentColor: Color = Color.White,
        val todayDateContainerColor: Color = Color.Blue,
    )

    data class Checkbox(
        val unselectedBackgroundColor: Color = Color.White,
        val unselectedStrokeColor: Color = Color.Black,
        val selectedBackgroundColor: Color = Color.Black,
        val selectedStrokeColor: Color = Color.Black,
        val selectedForegroundColor: Color = Color.White,
        val cornerRadius: Dp = 3.dp,
    )

    data class Radio(
        val unselectedBackgroundColor: Color = Color.White,
        val unselectedStrokeColor: Color = Color.Black,
        val selectedBackgroundColor: Color = Color.White,
        val selectedStrokeColor: Color = Color.Black,
        val selectedForegroundColor: Color = Color.Blue,
    )

    data class Rating(
        val unselectedBackgroundColor: Color = Color.Blue.copy(alpha = 0.3f),
        val unselectedStrokeColor: Color = Color.Blue.copy(alpha = 0.5f),
        val unselectedStrokeWidth: Dp = 1.dp,
        val unselectedForegroundColor: Color = Color.Black,
        val selectedBackgroundColor: Color = Color.Blue.copy(alpha = 0.3f),
        val selectedStrokeColor: Color = Color.Blue.copy(alpha = 0.9f),
        val selectedStrokeWidth: Dp = 2.dp,
        val selectedForegroundColor: Color = Color.Blue,
        val fillMaxWidth: Boolean = false,
    )
}
