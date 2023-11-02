package com.typeform.schema

data class Action(
    val action: ActionType,
    val details: ActionDetails,
    val condition: Condition,
) {
    companion object {
    }
}
