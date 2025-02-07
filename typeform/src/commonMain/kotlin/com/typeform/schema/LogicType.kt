package com.typeform.schema

import com.typeform.serializers.LogicTypeSerializer
import kotlinx.serialization.Serializable

@Serializable(with = LogicTypeSerializer::class)
enum class LogicType(val rawValue: String) {
    FIELD("field"),
}
