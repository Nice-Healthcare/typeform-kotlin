package com.typeform.schema

data class Logic(
    val ref: String,
    val type: LogicType,
    val actions: List<Action>,
) {
    companion object {
    }
}
