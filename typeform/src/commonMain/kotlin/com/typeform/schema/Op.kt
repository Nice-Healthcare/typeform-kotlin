package com.typeform.schema

import android.R.attr.entries
import com.typeform.serializers.OpSerializer
import kotlinx.serialization.Serializable

@Serializable(with = OpSerializer::class)
enum class Op(val rawValue: String) {
    ALWAYS("always"),
    AND("and"),
    EQUAL("equal"),
    GREATER_EQUAL_THAN("greater_equal_than"),
    GREATER_THAN("greater_than"),
    IS("is"),
    IS_NOT("is_not"),
    LOWER_EQUAL_THAN("lower_equal_than"),
    LOWER_THAN("lower_than"),
    OR("or"),
    ;

    companion object {
        fun fromRawValue(rawValue: String) = Op.entries.firstOrNull { it.rawValue == rawValue } ?: ALWAYS
    }
}
