package com.typeform.schema

enum class Op(val rawValue: String) {
    ALWAYS("always"),
    AND("and"),
    EQUAL("equal"),
    IS("is"),
    IS_NOT("is_not"),
    OR("or"),
    GREATER_EQUAL_THAN("greater_equal_than"),
    LOWER_EQUAL_THAN("lower_equal_than"),
    ;

    companion object {
        fun fromRawValue(rawValue: String) = entries.associateBy(Op::rawValue)[rawValue] ?: ALWAYS
    }
}
