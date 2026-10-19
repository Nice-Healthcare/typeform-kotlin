package com.typeform.ui.models

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

data class ColorConfiguration(
    val surface: Color = Color.Unspecified,
    val divider: Color = Color.Unspecified,
    val outlinedTextField: OutlinedTextField = OutlinedTextField(),
    val button: Button = Button(),
    val outlinedButton: OutlinedButton = OutlinedButton(),
    val textButton: TextButton = TextButton(),
    val iconButton: IconButton = IconButton(),
    val checkbox: Checkbox = Checkbox(),
    val radioButton: RadioButton = RadioButton(),
    val slider: Slider = Slider(),
    val switch: Switch = Switch(),
    val datePicker: DatePicker = DatePicker(),
) {
    data class OutlinedTextField(
        val textColor: Color = Color.Unspecified,
        val containerColor: Color = Color.Unspecified,
        val borderColor: Color = Color.Unspecified,
    )

    data class Button(
        val containerColor: Color = Color.Unspecified,
        val contentColor: Color = Color.Unspecified,
        val disabledContainerColor: Color = Color.Unspecified,
        val disabledContentColor: Color = Color.Unspecified,
    )

    data class OutlinedButton(
        val selectedContainerColor: Color = Color.Unspecified,
        val selectedContentColor: Color = Color.Unspecified,
        val selectedStrokeColor: Color = Color.Unspecified,
        val unselectedContainerColor: Color = Color.Unspecified,
        val unselectedContentColor: Color = Color.Unspecified,
        val unselectedStrokeColor: Color = Color.Unspecified,
    )

    data class TextButton(
        val containerColor: Color = Color.Unspecified,
        val contentColor: Color = Color.Unspecified,
    )

    data class IconButton(
        val containerColor: Color = Color.Unspecified,
        val contentColor: Color = Color.Unspecified,
    )

    data class Checkbox(
        val checkedColor: Color = Color.Unspecified,
        val uncheckedColor: Color = Color.Unspecified,
        val checkmarkColor: Color = Color.Unspecified,
    )

    data class RadioButton(
        val selectedColor: Color = Color.Unspecified,
        val unselectedColor: Color = Color.Unspecified,
    )

    data class Slider(
        val thumbColor: Color = Color.Unspecified,
        val trackColor: Color = Color.Unspecified,
        val tickColor: Color = Color.Unspecified,
    )

    data class Switch(
        val checkedThumbColor: Color = Color.Unspecified,
        val checkedTrackColor: Color = Color.Unspecified,
        val uncheckedThumbColor: Color = Color.Unspecified,
        val uncheckedTrackColor: Color = Color.Unspecified,
    )

    data class DatePicker(
        val containerColor: Color = Color.Unspecified,
        val dayContentColor: Color = Color.Unspecified,
        val selectedDayContentColor: Color = Color.Unspecified,
        val selectedDayContainerColor: Color = Color.Unspecified,
    )

    companion object {
        val lightExample: ColorConfiguration
            get() = ColorConfiguration(
                surface = Color.hsl(hue = 0f, saturation = 0f, lightness = 1f),
                divider = Color.hsl(hue = 240f, saturation = 0.20f, lightness = 0.94f),
                outlinedTextField = OutlinedTextField(
                    textColor = Color.hsl(hue = 240f, saturation = 0.11f, lightness = 0.23f),
                    containerColor = Color.hsl(hue = 0f, saturation = 0f, lightness = 1f),
                    borderColor = Color.hsl(hue = 240f, saturation = 0.10f, lightness = 0.61f),
                ),
                button = Button(
                    containerColor = Color.hsl(hue = 245f, saturation = 0.35f, lightness = 0.44f),
                    contentColor = Color.hsl(hue = 0f, saturation = 0f, lightness = 1f),
                    disabledContainerColor = Color.hsl(hue = 240f, saturation = 0.20f, lightness = 0.94f),
                    disabledContentColor = Color.hsl(hue = 270f, saturation = 0.07f, lightness = 0.48f),
                ),
                outlinedButton = OutlinedButton(
                    selectedContainerColor = Color.hsl(hue = 240f, saturation = 0.82f, lightness = 0.98f),
                    selectedContentColor = Color.hsl(hue = 240f, saturation = 0.11f, lightness = 0.23f),
                    selectedStrokeColor = Color.hsl(hue = 244f, saturation = 0.44f, lightness = 0.61f),
                    unselectedContainerColor = Color.hsl(hue = 240f, saturation = 0.82f, lightness = 0.98f),
                    unselectedContentColor = Color.hsl(hue = 240f, saturation = 0.11f, lightness = 0.23f),
                    unselectedStrokeColor = Color.hsl(hue = 240f, saturation = 0.76f, lightness = 0.84f),
                ),
                textButton = TextButton(
                    containerColor = Color.Transparent,
                    contentColor = Color.hsl(hue = 244f, saturation = 0.44f, lightness = 0.61f),
                ),
                iconButton = IconButton(
                    containerColor = Color.Transparent,
                    contentColor = Color.hsl(hue = 244f, saturation = 0.44f, lightness = 0.61f),
                ),
                checkbox = Checkbox(
                    checkedColor = Color.hsl(hue = 245f, saturation = 0.35f, lightness = 0.44f),
                    uncheckedColor = Color.hsl(hue = 245f, saturation = 0.35f, lightness = 0.44f),
                    checkmarkColor = Color.hsl(hue = 0f, saturation = 0f, lightness = 1f),
                ),
                radioButton = RadioButton(
                    selectedColor = Color.hsl(hue = 245f, saturation = 0.35f, lightness = 0.44f),
                    unselectedColor = Color.hsl(hue = 240f, saturation = 0.76f, lightness = 0.84f),
                ),
                slider = Slider(
                    thumbColor = Color.hsl(hue = 245f, saturation = 0.35f, lightness = 0.44f),
                    trackColor = Color.hsl(hue = 240f, saturation = 0.76f, lightness = 0.84f),
                    tickColor = Color.hsl(hue = 244f, saturation = 0.44f, lightness = 0.61f),
                ),
                switch = Switch(
                    checkedThumbColor = Color.hsl(hue = 245f, saturation = 0.35f, lightness = 0.44f),
                    checkedTrackColor = Color.hsl(hue = 240f, saturation = 0.76f, lightness = 0.84f),
                    uncheckedThumbColor = Color.hsl(hue = 240f, saturation = 0.10f, lightness = 0.61f),
                    uncheckedTrackColor = Color.hsl(hue = 240f, saturation = 0.20f, lightness = 0.94f),
                ),
                datePicker = DatePicker(
                    containerColor = Color.hsl(hue = 0f, saturation = 0f, lightness = 1f),
                    dayContentColor = Color.hsl(hue = 240f, saturation = 0.11f, lightness = 0.23f),
                    selectedDayContentColor = Color.hsl(hue = 0f, saturation = 0f, lightness = 1f),
                    selectedDayContainerColor = Color.hsl(hue = 245f, saturation = 0.35f, lightness = 0.44f),
                ),
            )

        val darkExample: ColorConfiguration
            get() = ColorConfiguration(
                surface = Color.hsl(hue = 240f, saturation = 0.12f, lightness = 0.10f),
                divider = Color.hsl(hue = 240f, saturation = 0.07f, lightness = 0.48f),
                outlinedTextField = OutlinedTextField(
                    textColor = Color.hsl(hue = 0f, saturation = 0f, lightness = 1f),
                    containerColor = Color.hsl(hue = 240f, saturation = 0.12f, lightness = 0.10f),
                    borderColor = Color.hsl(hue = 240f, saturation = 0.07f, lightness = 0.48f),
                ),
                button = Button(
                    containerColor = Color.hsl(hue = 240f, saturation = 0.76f, lightness = 0.84f),
                    contentColor = Color.hsl(hue = 240f, saturation = 0.11f, lightness = 0.23f),
                    disabledContainerColor = Color.hsl(hue = 240f, saturation = 0.07f, lightness = 0.48f),
                    disabledContentColor = Color.hsl(hue = 240f, saturation = 0.11f, lightness = 0.23f),
                ),
                outlinedButton = OutlinedButton(
                    selectedContainerColor = Color.hsl(hue = 240f, saturation = 0.11f, lightness = 0.23f),
                    selectedContentColor = Color.hsl(hue = 0f, saturation = 0f, lightness = 1f),
                    selectedStrokeColor = Color.hsl(hue = 240f, saturation = 0.76f, lightness = 0.84f),
                    unselectedContainerColor = Color.hsl(hue = 240f, saturation = 0.11f, lightness = 0.23f),
                    unselectedContentColor = Color.hsl(hue = 0f, saturation = 0f, lightness = 1f),
                    unselectedStrokeColor = Color.hsl(hue = 240f, saturation = 0.76f, lightness = 0.84f),
                ),
                textButton = TextButton(
                    containerColor = Color.Transparent,
                    contentColor = Color.hsl(hue = 240f, saturation = 0.76f, lightness = 0.84f),
                ),
                iconButton = IconButton(
                    containerColor = Color.Transparent,
                    contentColor = Color.hsl(hue = 240f, saturation = 0.76f, lightness = 0.84f),
                ),
                checkbox = Checkbox(
                    checkedColor = Color.hsl(hue = 240f, saturation = 0.76f, lightness = 0.84f),
                    uncheckedColor = Color.hsl(hue = 240f, saturation = 0.76f, lightness = 0.84f),
                    checkmarkColor = Color.hsl(hue = 240f, saturation = 0.11f, lightness = 0.23f),
                ),
                radioButton = RadioButton(
                    selectedColor = Color.hsl(hue = 240f, saturation = 0.76f, lightness = 0.84f),
                    unselectedColor = Color.hsl(hue = 240f, saturation = 0.76f, lightness = 0.84f),
                ),
                slider = Slider(
                    thumbColor = Color.hsl(hue = 0f, saturation = 0f, lightness = 1f),
                    trackColor = Color.hsl(hue = 244f, saturation = 0.44f, lightness = 0.61f),
                    tickColor = Color.hsl(hue = 240f, saturation = 1f, lightness = 0.93f),
                ),
                switch = Switch(
                    checkedThumbColor = Color.hsl(hue = 0f, saturation = 0f, lightness = 1f),
                    checkedTrackColor = Color.hsl(hue = 240f, saturation = 0.76f, lightness = 0.84f),
                    uncheckedThumbColor = Color.hsl(hue = 0f, saturation = 0f, lightness = 1f),
                    uncheckedTrackColor = Color.hsl(hue = 244f, saturation = 0.10f, lightness = 0.61f),
                ),
                datePicker = DatePicker(
                    containerColor = Color.hsl(hue = 240f, saturation = 0.12f, lightness = 0.10f),
                    dayContentColor = Color.hsl(hue = 0f, saturation = 0f, lightness = 1f),
                    selectedDayContentColor = Color.hsl(hue = 240f, saturation = 0.11f, lightness = 0.23f),
                    selectedDayContainerColor = Color.hsl(hue = 240f, saturation = 0.76f, lightness = 0.84f),
                ),
            )

        @Composable
        fun example(): ColorConfiguration {
            return if (isSystemInDarkTheme()) {
                darkExample
            } else {
                lightExample
            }
        }

        /**
         * Construct a [ColorConfiguration] using the MaterialTheme ColorScheme.
         *
         * This approach is very generic as to provide some-level of consistency. Depending
         * on your Material 3 usage, it may be best to use the default constructor where every
         * parameter is Unspecified.
         */
        @Composable
        fun construct(colorScheme: ColorScheme): ColorConfiguration =
            ColorConfiguration(
                surface = colorScheme.surface,
                divider = colorScheme.outline,
                outlinedTextField = OutlinedTextField(
                    textColor = colorScheme.onSurface,
                    containerColor = colorScheme.surfaceContainer,
                    borderColor = colorScheme.primaryContainer,
                ),
                button = Button(
                    containerColor = colorScheme.primary,
                    contentColor = colorScheme.onPrimary,
                    disabledContainerColor = colorScheme.primaryFixedDim,
                    disabledContentColor = colorScheme.onPrimaryFixed,
                ),
                outlinedButton = OutlinedButton(
                    selectedContainerColor = colorScheme.surfaceContainer,
                    selectedContentColor = colorScheme.onSurface,
                    selectedStrokeColor = colorScheme.primary,
                    unselectedContainerColor = colorScheme.surfaceContainer,
                    unselectedContentColor = colorScheme.onSurface,
                    unselectedStrokeColor = colorScheme.primaryFixed,
                ),
                textButton = TextButton(
                    containerColor = Color.Transparent,
                    contentColor = colorScheme.primary,
                ),
                iconButton = IconButton(
                    containerColor = Color.Transparent,
                    contentColor = colorScheme.primary,
                ),
                checkbox = Checkbox(
                    checkedColor = colorScheme.primary,
                    uncheckedColor = colorScheme.primaryFixed,
                    checkmarkColor = colorScheme.onPrimary,
                ),
                radioButton = RadioButton(
                    selectedColor = colorScheme.primary,
                    unselectedColor = colorScheme.primaryFixed,
                ),
                slider = Slider(
                    thumbColor = colorScheme.onPrimary,
                    trackColor = colorScheme.primaryContainer,
                    tickColor = colorScheme.onPrimaryContainer,
                ),
                switch = Switch(
                    checkedThumbColor = colorScheme.onPrimary,
                    checkedTrackColor = colorScheme.primary,
                    uncheckedThumbColor = colorScheme.onPrimaryFixed,
                    uncheckedTrackColor = colorScheme.primaryFixed,
                ),
                datePicker = DatePicker(
                    containerColor = colorScheme.surfaceContainer,
                    dayContentColor = colorScheme.onSurface,
                    selectedDayContentColor = colorScheme.onPrimary,
                    selectedDayContainerColor = colorScheme.primary,
                ),
            )

        /**
         * Construct a [ColorConfiguration] using the MaterialTheme Colors.
         *
         * This is an overly generalized approach. For best results when using Material 1/2,
         * manually declare all-of-the configuration parameters.
         */
        @Composable
        fun construct(colors: Colors): ColorConfiguration =
            ColorConfiguration(
                surface = colors.surface,
                divider = colors.onSurface,
            )
    }

    val outlinedTextFieldColors: TextFieldColors
        @Composable
        get() = OutlinedTextFieldDefaults.colors(
            focusedTextColor = outlinedTextField.textColor,
            unfocusedTextColor = outlinedTextField.textColor,
            focusedContainerColor = outlinedTextField.containerColor,
            unfocusedContainerColor = outlinedTextField.containerColor,
            focusedBorderColor = outlinedTextField.borderColor,
            unfocusedBorderColor = outlinedTextField.borderColor,
        )

    val buttonColors: ButtonColors
        @Composable
        get() = ButtonDefaults.buttonColors(
            containerColor = button.containerColor,
            contentColor = button.contentColor,
            disabledContainerColor = button.disabledContainerColor,
            disabledContentColor = button.disabledContentColor,
        )

    @Composable
    fun outlinedButtonColors(selected: Boolean): ButtonColors =
        ButtonDefaults.outlinedButtonColors(
            containerColor = if (selected) outlinedButton.selectedContainerColor else outlinedButton.unselectedContainerColor,
            contentColor = if (selected) outlinedButton.selectedContentColor else outlinedButton.unselectedContentColor,
        )

    val iconButtonColors: IconButtonColors
        @Composable
        get() = IconButtonDefaults.iconButtonColors(
            containerColor = iconButton.containerColor,
            contentColor = iconButton.contentColor,
        )

    val textButtonColors: ButtonColors
        @Composable
        get() = ButtonDefaults.textButtonColors(
            containerColor = textButton.containerColor,
            contentColor = textButton.contentColor,
        )

    val checkboxColors: CheckboxColors
        @Composable
        get() = CheckboxDefaults.colors(
            checkedColor = checkbox.checkedColor,
            uncheckedColor = checkbox.uncheckedColor,
            checkmarkColor = checkbox.checkmarkColor,
        )

    val radioButtonColors: RadioButtonColors
        @Composable
        get() = RadioButtonDefaults.colors(
            selectedColor = radioButton.selectedColor,
            unselectedColor = radioButton.unselectedColor,
        )

    val sliderColors: SliderColors
        @Composable
        get() = SliderDefaults.colors(
            thumbColor = slider.thumbColor,
            activeTrackColor = slider.trackColor,
            activeTickColor = slider.tickColor,
            inactiveTrackColor = slider.trackColor,
            inactiveTickColor = slider.tickColor,
        )

    val switchColors: SwitchColors
        @Composable
        get() = SwitchDefaults.colors(
            checkedThumbColor = switch.checkedThumbColor,
            checkedTrackColor = switch.checkedTrackColor,
            uncheckedThumbColor = switch.uncheckedThumbColor,
            uncheckedTrackColor = switch.uncheckedTrackColor,
            uncheckedBorderColor = switch.uncheckedThumbColor,
        )

    val datePickerColors: DatePickerColors
        @Composable
        get() = DatePickerDefaults.colors(
            containerColor = datePicker.containerColor,
            dayContentColor = datePicker.dayContentColor,
            selectedDayContentColor = datePicker.selectedDayContentColor,
            selectedDayContainerColor = datePicker.selectedDayContainerColor,
        )
}
