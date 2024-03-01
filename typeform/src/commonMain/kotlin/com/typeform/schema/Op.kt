package com.typeform.schema

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
        fun fromRawValue(rawValue: String) = entries.associateBy(Op::rawValue)[rawValue] ?: ALWAYS
    }
}
