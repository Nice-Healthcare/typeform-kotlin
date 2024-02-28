package com.typeform.schema

enum class FieldType(val rawValue: String) {
    DATE("date"),
    DROPDOWN("dropdown"),
    GROUP("group"),
    LONG_TEXT("long_text"),
    MULTIPLE_CHOICE("multiple_choice"),
    NUMBER("number"),
    OPINION_SCALE("opinion_scale"),
    RATING("rating"),
    SHORT_TEXT("short_text"),
    STATEMENT("statement"),
    YES_NO("yes_no"),
    ;

    companion object {
        fun fromRawValue(rawValue: String) = FieldType.values().associateBy(FieldType::rawValue)[rawValue] ?: STATEMENT
    }
}
