package com.typeform.schema.questions

import kotlinx.serialization.Serializable

@Serializable
data class Rating(
    val shape: String,
    val steps: Int,
    val description: String?,
)
