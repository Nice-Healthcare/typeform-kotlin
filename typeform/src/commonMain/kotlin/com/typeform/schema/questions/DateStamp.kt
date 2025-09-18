package com.typeform.schema.questions

import kotlinx.serialization.Serializable

@Serializable
data class DateStamp(
    val separator: String,
    val structure: String,
    val description: String?,
)
