package com.typeform.schema

import com.typeform.serializers.ActionTypeSerializer
import kotlinx.serialization.Serializable

@Serializable(with = ActionTypeSerializer::class)
enum class ActionType(val rawValue: String) {
    JUMP("jump"),
}
