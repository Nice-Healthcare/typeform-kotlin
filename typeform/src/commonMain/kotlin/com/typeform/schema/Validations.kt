package com.typeform.schema

import kotlinx.serialization.Serializable

@Serializable
data class Validations(
    val required: Boolean = false,
)
