package com.typeform.schema

enum class ActionType(val rawValue: String) {
    JUMP("jump"),
    ;

    companion object {
        fun fromRawValue(rawValue: String) = ActionType.values().associateBy(ActionType::rawValue)[rawValue] ?: JUMP
    }
}
