package com.typeform.schema

data class OpinionScale(
    val steps: Int,
    val labels: Labels,
    val start_at_one: Boolean
) {
    data class Labels(
        val left: String = "",
        val right: String = "",
    )
}
