package com.typeform.schema

enum class VarType(val rawValue: String) {
    CHOICE("choice"),
    CONSTANT("constant"),
    FIELD("field"),
    ;

    companion object {
        fun fromRawValue(rawValue: String) = VarType.entries.firstOrNull { it.rawValue == rawValue } ?: CONSTANT
    }
}
