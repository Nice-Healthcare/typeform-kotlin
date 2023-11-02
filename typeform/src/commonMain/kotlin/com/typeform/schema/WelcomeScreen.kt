package com.typeform.schema

data class WelcomeScreen(
    override val id: String,
    val ref: String,
    override val title: String,
    override val attachment: ScreenAttachment?,
    override val properties: ScreenProperties,
) : Screen
