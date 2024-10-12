package com.typeform.schema

import com.typeform.serializers.ActionSerializer
import kotlinx.serialization.Serializable

@Serializable(with = ActionSerializer::class)
data class Action(
    val action: ActionType,
    val details: ActionDetails,
    val condition: Condition,
) {
    companion object {
    }
}
