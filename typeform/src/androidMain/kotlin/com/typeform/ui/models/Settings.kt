package com.typeform.ui.models

/**
 * Control the layout behaviors and styling of a [com.typeform.schema.structure.Form] presentation.
 *
 * @param localization String values used in the presentation of the form.
 * @param presentation General padding & spacing applied across every screen.
 * @param appearance Typography styles applied to the form components.
 */
data class Settings(
    val localization: Localization = Localization(),
    val presentation: Presentation = Presentation(),
    val appearance: Appearance = Appearance(),
)
