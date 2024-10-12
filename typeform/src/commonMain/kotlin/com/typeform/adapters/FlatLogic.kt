package com.typeform.adapters

import com.typeform.schema.Action
import com.typeform.schema.Logic
import com.typeform.schema.LogicType

@Deprecated(message = "Use kotlinx-serialization.", replaceWith = ReplaceWith("LogicContract"))
data class FlatLogic(
    val ref: String,
    val type: LogicType,
    val actions: List<FlatAction>,
) {
    companion object {
    }
}

fun Logic.Companion.make(flatLogic: FlatLogic): Logic {
    return Logic(
        ref = flatLogic.ref,
        type = flatLogic.type,
        actions = flatLogic.actions.map { Action.make(it) },
    )
}

fun FlatLogic.Companion.make(logic: Logic): FlatLogic {
    return FlatLogic(
        ref = logic.ref,
        type = logic.type,
        actions = logic.actions.map { FlatAction.make(it) },
    )
}
