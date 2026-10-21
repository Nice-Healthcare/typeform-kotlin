package com.typeform.ui.models

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonColors
import androidx.compose.material.CheckboxColors
import androidx.compose.material.RadioButtonColors
import androidx.compose.material.SliderColors
import androidx.compose.material.SwitchColors
import androidx.compose.material.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Control the layout behaviors and styling of a [com.typeform.schema.structure.Form] presentation.
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
@Deprecated(message = "See [LocalizationConfiguration], [PresentationConfiguration], [TypographyConfiguration], & [ColorConfiguration]")
data class Settings(
    val localization: Localization = Localization(),
    val presentation: Presentation = Presentation(),
    val callToAction: CallToAction = CallToAction(),
    val field: Field = Field(),
    val calendar: Calendar = Calendar(),
    val switch: Switch = Switch(),
    val button: Button = Button(),
    val outlinedButton: OutlinedButton = OutlinedButton(),
    val checkbox: Checkbox = Checkbox(),
    val radio: Radio = Radio(),
    val rating: Rating = Rating(),
    val opinionScale: OpinionScale = OpinionScale(),
    val upload: Upload = Upload(),
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

    @Deprecated(message = "", replaceWith = ReplaceWith("LocalizationConfiguration"))
    typealias Localization = LocalizationConfiguration

    @Deprecated(message = "", replaceWith = ReplaceWith("PresentationConfiguration"))
    data class Presentation(
        @Deprecated(message = "", replaceWith = ReplaceWith("[PresentationConfiguration.headlineContainerSpacing]"))
        val titleDescriptionVerticalSpacing: Dp = 30.dp,
        @Deprecated(message = "", replaceWith = ReplaceWith("[PresentationConfiguration.contentContainerSpacing]"))
        val descriptionContentVerticalSpacing: Dp = 30.dp,
        @Deprecated(message = "", replaceWith = ReplaceWith("[PresentationConfiguration.topBarPadding]"))
        val topBarPadding: PaddingValues = PaddingValues(10.dp),
        @Deprecated(message = "", replaceWith = ReplaceWith("[PresentationConfiguration.contentPadding]"))
        val contentPadding: PaddingValues = PaddingValues(10.dp),
        @Deprecated(message = "", replaceWith = ReplaceWith("[PresentationConfiguration.elementSpacing]"))
        val contentVerticalSpacing: Dp = 15.dp,
        @Deprecated(message = "", replaceWith = ReplaceWith("[PresentationConfiguration.elementSpacing]"))
        val contentHorizontalSpacing: Dp = 10.dp,
        @Deprecated(message = "Ignored")
        val showWelcomeImage: Boolean = true,
        @Deprecated(message = "Ignored")
        val showThankYouImage: Boolean = true,
        @Deprecated(message = "Provide value direct to FormView")
        val skipWelcomeScreen: Boolean = false,
        @Deprecated(message = "Provide value direct to FormView")
        val skipEndingScreen: Boolean = false,
    )

    @Deprecated(message = "See [ColorConfiguration] & [PresentationConfiguration]")
    data class CallToAction(
        val colors: ButtonColors = TypeformButtonColors.defaultColors,
        val backgroundColor: Color = Color.Gray,
        val dividerColor: Color = Color.Black,
        val containerPadding: PaddingValues = PaddingValues(10.dp),
        val contentPadding: PaddingValues = PaddingValues(10.dp),
    )

    @Deprecated(message = "See [ColorConfiguration] & [PresentationConfiguration]")
    data class Field(
        val colors: TextFieldColors = TypeformTextFieldColors(),
        val strokeWidth: Dp = 1.dp,
        val verticalPadding: Dp = 10.dp,
        val horizontalPadding: Dp = 15.dp,
    )

    @Deprecated(message = "See [ColorConfiguration]")
    data class Calendar(
        val weekdayContentColor: Color = Color.Black,
        val dayContentColor: Color = Color.Black,
        val selectedDayContentColor: Color = Color.White,
        val selectedDayContainerColor: Color = Color.Blue,
        val todayDateContentColor: Color = Color.White,
        val todayDateContainerColor: Color = Color.Blue,
    )

    @Deprecated(message = "See [ColorConfiguration]")
    data class Switch(
        val colors: SwitchColors = TypeformSwitchColors(),
    )

    @Deprecated(message = "See [ColorConfiguration] & [PresentationConfiguration]")
    data class Button(
        val colors: ButtonColors = TypeformButtonColors.defaultColors,
        val unselectedBorderStroke: BorderStroke? = defaultUnselectedButtonBorderStroke,
        val selectedBorderStroke: BorderStroke? = defaultSelectedButtonBorderStroke,
        val contentPadding: PaddingValues = PaddingValues(10.dp),
    )

    @Deprecated(message = "See [ColorConfiguration] & [PresentationConfiguration]")
    data class OutlinedButton(
        val colors: ButtonColors = TypeformButtonColors.outlinedColors,
        val shape: Shape = RoundedCornerShape(6.dp),
        val border: BorderStroke? = defaultSelectedButtonBorderStroke,
        val contentPadding: PaddingValues = PaddingValues(10.dp),
    )

    @Deprecated(message = "See [ColorConfiguration]")
    data class Checkbox(
        val colors: CheckboxColors = TypeformCheckboxColors(),
    )

    @Deprecated(message = "See [ColorConfiguration]")
    data class Radio(
        val colors: RadioButtonColors = TypeformRadioButtonColors(),
    )

    @Deprecated(message = "See [ColorConfiguration]")
    data class Rating(
        val colors: ButtonColors = TypeformButtonColors.ratingColors,
    )

    @Deprecated(message = "See [ColorConfiguration]")
    data class OpinionScale(
        val colors: SliderColors = TypeformSliderColors.opinionScaleColors,
    )

    @Deprecated(message = "See [ColorConfiguration] & [PresentationConfiguration]")
    data class Upload(
        val colors: ButtonColors = TypeformButtonColors.uploadColors,
        val shape: Shape = RoundedCornerShape(16.dp),
        val maxWidth: Dp? = 200.dp,
    )

    val presentationConfiguration: PresentationConfiguration
        get() = PresentationConfiguration(
            topBarPadding = presentation.topBarPadding,
            contentPadding = presentation.contentPadding,
            elementPadding = PaddingValues(all = presentation.contentHorizontalSpacing),
            headlineContainerSpacing = presentation.titleDescriptionVerticalSpacing,
            contentContainerSpacing = presentation.descriptionContentVerticalSpacing,
            elementSpacing = presentation.contentHorizontalSpacing,
            buttonContentPadding = callToAction.containerPadding,
            outlinedButtonContentPadding = outlinedButton.contentPadding,
        )

    val colorConfiguration: ColorConfiguration
        @Composable
        get() = ColorConfiguration(
            outlinedTextField = ColorConfiguration.OutlinedTextField(
                textColor = this.field.colors.textColor(true).value,
                containerColor = this.field.colors.backgroundColor(true).value,
            ),
        )
}
