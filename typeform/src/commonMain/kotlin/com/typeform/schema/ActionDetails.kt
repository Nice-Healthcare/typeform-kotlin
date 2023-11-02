package com.typeform.schema

data class ActionDetails(
    val to: To,
) {
    data class To(
        val type: ToType,
        val value: String,
    )

    enum class ToType(val rawValue: String) {
        FIELD("field"),
        THANK_YOU("thankyou"),
        ;

        companion object {
            fun fromRawValue(rawValue: String) = ToType.values().associateBy(ToType::rawValue)[rawValue] ?: THANK_YOU
        }
    }
}
