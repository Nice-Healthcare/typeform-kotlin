package com.typeform.schema

enum class LogicType(val rawValue: String) {
    FIELD("field"),
    ;

    companion object {
        fun fromRawValue(rawValue: String) = LogicType.values().associateBy(LogicType::rawValue)[rawValue] ?: FIELD
    }
}
