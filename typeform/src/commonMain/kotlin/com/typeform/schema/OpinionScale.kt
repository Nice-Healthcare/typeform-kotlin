package com.typeform.schema

import kotlinx.serialization.Serializable

@Serializable
data class OpinionScale(
    val steps: Int,
    val labels: Labels?,
    val start_at_one: Boolean,
) {
    @Serializable
    data class Labels(
        val left: String = "",
        val right: String = "",
    )
}
