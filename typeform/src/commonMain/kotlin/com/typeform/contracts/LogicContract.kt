package com.typeform.contracts

import com.typeform.schema.Logic
import com.typeform.schema.LogicType
import kotlinx.serialization.Serializable

@Serializable
data class LogicContract(
    val ref: String,
    val type: LogicType,
    val actions: List<ActionContract>
) {
    constructor(logic: Logic): this(
        ref = logic.ref,
        type = logic.type,
        actions = logic.actions.map { ActionContract(it) }
    )

    fun toLogic(): Logic {
        return Logic(
            ref = ref,
            type = type,
            actions = actions.map { it.toAction() },
        )
    }
}