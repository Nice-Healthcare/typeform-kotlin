package com.typeform.ui.models

@Deprecated(message = "Avoid confusion with [com.typeform.schema.structure.Settings]", replaceWith = ReplaceWith("Apperance"))
typealias Settings = Appearance

/**
 * Control the layout behaviors and styling of a [com.typeform.schema.structure.Form] presentation.
 *
 * @param localization String values used in the presentation of the form.
 * @param presentation General padding & spacing applied across every screen.
 * @param textStyles Typography styles applied to the form components.
 * @param additionalLogic Addition settings that manipulate the display & flow of a form.
 */
data class Appearance(
    val localization: Localization = Localization(),
    val presentation: Presentation = Presentation(),
    val textStyles: TextStyles = TextStyles(),
    val additionalLogic: AdditionalLogic = AdditionalLogic(),
)
