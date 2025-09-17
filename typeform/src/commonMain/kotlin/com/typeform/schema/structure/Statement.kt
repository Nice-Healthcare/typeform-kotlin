package com.typeform.schema.structure

import kotlinx.serialization.Serializable

@Serializable
data class Statement(
    val hide_marks: Boolean,
    val button_text: String,
    val description: String?,
)
