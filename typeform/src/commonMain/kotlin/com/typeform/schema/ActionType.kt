package com.typeform.schema

import com.typeform.serializers.ActionTypeSerializer
import kotlinx.serialization.Serializable

@Serializable(with = ActionTypeSerializer::class)
enum class ActionType(val rawValue: String) {
    JUMP("jump"),
    ;

    companion object {
        fun fromRawValue(rawValue: String) = ActionType.entries.firstOrNull { it.rawValue == rawValue } ?: JUMP
    }
}
