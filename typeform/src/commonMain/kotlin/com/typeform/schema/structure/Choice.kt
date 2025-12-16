package com.typeform.schema.structure

import kotlinx.serialization.Serializable

@Serializable
data class Choice(
    val id: String = "",
    val ref: String = "",
    val label: String = "",
) {
    companion object {
    }
}
