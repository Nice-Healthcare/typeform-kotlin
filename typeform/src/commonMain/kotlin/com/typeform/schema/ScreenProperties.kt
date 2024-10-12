package com.typeform.schema

import kotlinx.serialization.Serializable

@Serializable
data class ScreenProperties(
    val button_mode: String?,
    val button_text: String?,
    val share_icons: Boolean?,
    val show_button: Boolean,
)
