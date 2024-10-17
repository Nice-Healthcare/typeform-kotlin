package com.typeform.adapters

import com.typeform.schema.Action
import com.typeform.schema.ActionDetails
import com.typeform.schema.ActionType
import com.typeform.schema.Condition

@Deprecated(message = "Use kotlinx-serialization.", replaceWith = ReplaceWith("ActionContract"))
data class FlatAction(
    val action: ActionType,
    val details: ActionDetails,
    val condition: FlatCondition,
) {
    companion object {
    }
}

fun Action.Companion.make(flatAction: FlatAction): Action {
    return Action(
        action = flatAction.action,
        details = flatAction.details,
        condition = Condition.makeCondition(flatAction.condition) ?: Condition(),
    )
}

fun FlatAction.Companion.make(action: Action): FlatAction {
    return FlatAction(
        action = action.action,
        details = action.details,
        condition = FlatCondition.make(action.condition),
    )
}
