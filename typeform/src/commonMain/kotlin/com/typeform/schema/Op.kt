package com.typeform.schema

enum class Op(val rawValue: String) {
    ALWAYS("always"),
    AND("and"),
    EQUAL("equal"),
    IS("is"),
    IS_NOT("is_not"),
    OR("or"),
    ;

    companion object {
        fun fromRawValue(rawValue: String) = Op.values().associateBy(Op::rawValue)[rawValue] ?: ALWAYS
    }
}
