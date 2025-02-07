package com.typeform.schema

import com.typeform.serializers.ActionDetailsToTypeSerializer
import kotlinx.serialization.Serializable

@Serializable
data class ActionDetails(
    val to: To,
) {
    @Serializable
    data class To(
        val type: ToType,
        val value: String,
    )

    @Serializable(with = ActionDetailsToTypeSerializer::class)
    enum class ToType(val rawValue: String) {
        FIELD("field"),
        THANK_YOU("thankyou"),
    }
}
