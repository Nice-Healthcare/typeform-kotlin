package com.typeform.adapters

import com.typeform.schema.Condition
import com.typeform.schema.Op
import com.typeform.schema.Var
import com.typeform.schema.VarType

data class FlatCondition(
    val op: Op? = null,
    val vars: List<FlatCondition>? = null,
    val type: VarType? = null,
    val value: Any? = null,
) {
    companion object {
    }
}

fun Condition.Companion.makeCondition(flatCondition: FlatCondition): Condition? {
    if (flatCondition.op != null && flatCondition.vars != null) {
        val conditions = flatCondition.vars.mapNotNull { Condition.makeCondition(it) }
        val vars = flatCondition.vars.mapNotNull { Condition.makeVar(it) }

        return if (conditions.isNotEmpty()) {
            Condition(
                op = flatCondition.op,
                parameters = Condition.Parameters.Conditions(conditions),
            )
        } else {
            Condition(
                op = flatCondition.op,
                parameters = Condition.Parameters.Vars(vars),
            )
        }
    }

    return null
}

fun Condition.Companion.makeVar(flatCondition: FlatCondition): Var? {
    if (flatCondition.type != null && flatCondition.value != null) {
        return when (flatCondition.value) {
            is Boolean -> {
                Var(
                    type = flatCondition.type,
                    value = Var.Value.Bool(flatCondition.value),
                )
            }
            is Int -> {
                Var(
                    type = flatCondition.type,
                    value = Var.Value.Integer(flatCondition.value),
                )
            }
            is Double -> {
                Var(
                    type = flatCondition.type,
                    value = Var.Value.Integer(flatCondition.value.toInt()),
                )
            }
            is String -> {
                Var(
                    type = flatCondition.type,
                    value = Var.Value.RefOrString(flatCondition.value),
                )
            }
            else -> {
                null
            }
        }
    }

    return null
}

fun FlatCondition.Companion.make(condition: Condition): FlatCondition {
    val vars = when (condition.parameters) {
        is Condition.Parameters.Vars -> {
            condition.parameters.vars.map { FlatCondition.make(it) }
        }
        is Condition.Parameters.Conditions -> {
            condition.parameters.conditions.map { FlatCondition.make(it) }
        }
    }

    return FlatCondition(
        op = condition.op,
        vars = vars,
    )
}

fun FlatCondition.Companion.make(`var`: Var): FlatCondition {
    return when (`var`.value) {
        is Var.Value.Bool -> {
            FlatCondition(
                type = `var`.type,
                value = `var`.value.value,
            )
        }
        is Var.Value.Integer -> {
            FlatCondition(
                type = `var`.type,
                value = `var`.value.value,
            )
        }
        is Var.Value.RefOrString -> {
            FlatCondition(
                type = `var`.type,
                value = `var`.value.value,
            )
        }
    }
}
