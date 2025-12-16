package com.typeform.schema.structure

import kotlinx.serialization.Serializable

@Serializable
data class Validations(
    val required: Boolean = false,
)
