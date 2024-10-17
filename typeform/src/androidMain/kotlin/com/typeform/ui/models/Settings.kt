package com.typeform.ui.models

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.ButtonColors
import androidx.compose.material.CheckboxColors
import androidx.compose.material.RadioButtonColors
import androidx.compose.material.SliderColors
import androidx.compose.material.SwitchColors
import androidx.compose.material.TextFieldColors
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Control the layout behaviors and styling of a [com.typeform.schema.Form] presentation.
 *
 * @param localization String values used in the presentation of the form.
 * @param presentation General padding & spacing applied across every screen.
 * @param callToAction Colors and paddings applied to the CTA (Call-to-Action) button & container.
 * @param field Style information applied to text entry fields.
 * @param calendar Settings for a [androidx.compose.material3.DatePicker] component.
 * @param switch Settings applied to a [androidx.compose.material.Switch].
 * @param button Settings for general [androidx.compose.material.Button]s used on many screens.
 * @param checkbox Settings for a [androidx.compose.material.Checkbox].
 * @param radio Settings for a [androidx.compose.material.RadioButton].
 * @param rating Settings applied to a [com.typeform.ui.fields.RatingView] component.
 * @param opinionScale Settings applied to a [com.typeform.ui.fields.OpinionScaleView] component.
 */
data class Settings(
    val localization: Localization = Localization(),
    val presentation: Presentation = Presentation(),
    val callToAction: CallToAction = CallToAction(),
    val field: Field = Field(),
    @Deprecated("Use individual component styling.")
    val interaction: Interaction = Interaction(),
    val calendar: Calendar = Calendar(),
    val switch: Switch = Switch(),
    val button: Button = Button(),
    val checkbox: Checkbox = Checkbox(),
    val radio: Radio = Radio(),
    val rating: Rating = Rating(),
    val opinionScale: OpinionScale = OpinionScale(),
) {
    companion object {
        val defaultUnselectedButtonBorderStroke = BorderStroke(
            width = 1.dp,
            color = Color.Black,
        )

        val defaultSelectedButtonBorderStroke = BorderStroke(
            width = 2.dp,
            color = Color.Blue,
        )
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
        val colors: ButtonColors = TypeformButtonColors.defaultColors,
        val backgroundColor: Color = Color.Gray,
        val dividerColor: Color = Color.Black,
        val containerPadding: PaddingValues = PaddingValues(10.dp),
        val contentPadding: PaddingValues = PaddingValues(10.dp),
    )

    data class Field(
        val colors: TextFieldColors = TypeformTextFieldColors(),
        val strokeWidth: Dp = 1.dp,
        val verticalPadding: Dp = 10.dp,
        val horizontalPadding: Dp = 15.dp,
    )

    @Deprecated("")
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

    data class Switch(
        val colors: SwitchColors = TypeformSwitchColors(),
    )

    data class Button(
        val colors: ButtonColors = TypeformButtonColors.defaultColors,
        val unselectedBorderStroke: BorderStroke? = defaultUnselectedButtonBorderStroke,
        val selectedBorderStroke: BorderStroke? = defaultSelectedButtonBorderStroke,
        val contentPadding: PaddingValues = PaddingValues(10.dp),
    )

    data class Checkbox(
        val colors: CheckboxColors = TypeformCheckboxColors(),
        @Deprecated("")
        val unselectedBackgroundColor: Color = Color.White,
        @Deprecated("")
        val unselectedStrokeColor: Color = Color.Black,
        @Deprecated("")
        val selectedBackgroundColor: Color = Color.Black,
        @Deprecated("")
        val selectedStrokeColor: Color = Color.Black,
        @Deprecated("")
        val selectedForegroundColor: Color = Color.White,
        @Deprecated("")
        val cornerRadius: Dp = 3.dp,
    )

    data class Radio(
        val colors: RadioButtonColors = TypeformRadioButtonColors(),
        @Deprecated("")
        val unselectedBackgroundColor: Color = Color.White,
        @Deprecated("")
        val unselectedStrokeColor: Color = Color.Black,
        @Deprecated("")
        val selectedBackgroundColor: Color = Color.White,
        @Deprecated("")
        val selectedStrokeColor: Color = Color.Black,
        @Deprecated("")
        val selectedForegroundColor: Color = Color.Blue,
    )

    data class Rating(
        val colors: ButtonColors = TypeformButtonColors.ratingColors,
        @Deprecated("")
        val unselectedBackgroundColor: Color = Color.Blue.copy(alpha = 0.3f),
        @Deprecated("")
        val unselectedStrokeColor: Color = Color.Blue.copy(alpha = 0.5f),
        @Deprecated("")
        val unselectedStrokeWidth: Dp = 1.dp,
        @Deprecated("")
        val unselectedForegroundColor: Color = Color.Black,
        @Deprecated("")
        val selectedBackgroundColor: Color = Color.Blue.copy(alpha = 0.3f),
        @Deprecated("")
        val selectedStrokeColor: Color = Color.Blue.copy(alpha = 0.9f),
        @Deprecated("")
        val selectedStrokeWidth: Dp = 2.dp,
        @Deprecated("")
        val selectedForegroundColor: Color = Color.Blue,
    )

    data class OpinionScale(
        val colors: SliderColors = TypeformSliderColors.opinionScaleColors,
    )
}
