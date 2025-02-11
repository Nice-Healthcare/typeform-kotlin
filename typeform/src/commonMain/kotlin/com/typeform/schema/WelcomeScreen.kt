package com.typeform.schema

import kotlinx.serialization.Serializable

@Serializable
data class WelcomeScreen(
    override val id: String,
    val ref: String,
    override val title: String,
    override val attachment: Attachment?,
    override val properties: ScreenProperties,
) : Screen
