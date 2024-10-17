package com.typeform.contracts

import com.typeform.schema.Action
import com.typeform.schema.ActionDetails
import com.typeform.schema.ActionType
import com.typeform.schema.Condition
import kotlinx.serialization.Serializable

@Serializable
data class ActionContract(
    val action: ActionType,
    val details: ActionDetails,
    val condition: ConditionContract,
) {
    constructor(action: Action) : this(
        action = action.action,
        details = action.details,
        condition = ConditionContract(action.condition),
    )

    fun toAction(): Action {
        return Action(
            action = action,
            details = details,
            condition = condition.toCondition() ?: Condition(),
        )
    }
}
