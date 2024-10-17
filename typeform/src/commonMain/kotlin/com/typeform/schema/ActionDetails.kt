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
        ;

        companion object {
            fun fromRawValue(rawValue: String) = ToType.entries.firstOrNull { it.rawValue == rawValue } ?: THANK_YOU
        }
    }
}
