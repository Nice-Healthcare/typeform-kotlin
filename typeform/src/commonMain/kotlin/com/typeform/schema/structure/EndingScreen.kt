package com.typeform.schema.structure

import kotlinx.serialization.Serializable

@Deprecated(message = "", replaceWith = ReplaceWith("EndingScreen"))
typealias ThankYouScreen = EndingScreen

@Serializable
data class EndingScreen(
    override val id: String,
    val ref: String,
    val type: String,
    override val title: String,
    override val attachment: Attachment?,
    override val properties: ScreenProperties,
) : Screen {
    /**
     * Indicates whether this [ThankYouScreen] is considered the _default_ for the [com.typeform.schema.Form].
     */
    val isDefault: Boolean
        get() = ref.compareTo("default_tys", true) == 0
}
