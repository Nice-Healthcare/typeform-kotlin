package com.typeform.schema

enum class FormType(val rawValue: String) {
    QUIZ("quiz"),
    ;

    companion object {
        fun fromRawValue(rawValue: String) = FormType.values().associateBy(FormType::rawValue)[rawValue] ?: QUIZ
    }
}
