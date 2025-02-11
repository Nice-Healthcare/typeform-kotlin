package com.typeform.schema

import kotlinx.serialization.Serializable

@Serializable
data class ThankYouScreen(
    override val id: String,
    val ref: String,
    val type: String,
    override val title: String,
    override val attachment: Attachment?,
    override val properties: ScreenProperties,
) : Screen {
    /**
     * Indicates whether this [ThankYouScreen] is considered the _default_ for the [Form].
     */
    val isDefault: Boolean
        get() = ref.compareTo("default_tys", true) == 0
}
