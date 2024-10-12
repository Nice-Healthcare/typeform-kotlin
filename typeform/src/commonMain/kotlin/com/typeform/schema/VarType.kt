package com.typeform.schema

import com.typeform.serializers.VarTypeSerializer
import kotlinx.serialization.Serializable

@Serializable(with = VarTypeSerializer::class)
enum class VarType(val rawValue: String) {
    CHOICE("choice"),
    CONSTANT("constant"),
    FIELD("field"),
    ;

    companion object {
        fun fromRawValue(rawValue: String) = VarType.entries.firstOrNull { it.rawValue == rawValue } ?: CONSTANT
    }
}
