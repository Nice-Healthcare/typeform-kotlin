package com.typeform.contracts

import com.typeform.schema.Condition
import com.typeform.schema.Op
import com.typeform.schema.Var
import com.typeform.schema.VarType
import kotlinx.serialization.Serializable

@Serializable
data class ConditionContract(
    val op: Op?,
    val vars: List<ConditionContract>?,
    val type: VarType?,
    val value: Var.Value?,
) {
    constructor(condition: Condition): this(
        op = condition.op,
        vars = when (condition.parameters) {
                is Condition.Parameters.Vars -> {
                    condition.parameters.vars.map { ConditionContract(it) }
                }
                is Condition.Parameters.Conditions -> {
                    condition.parameters.conditions.map { ConditionContract(it) }
                }
            },
        type = null,
        value = null
    )

    constructor(`var`: Var): this(
        op = null,
        vars = null,
        type = `var`.type,
        value = `var`.value,
    )

    fun toCondition(): Condition? {
        if (op != null && vars != null) {
            val conditions = vars.mapNotNull { it.toCondition() }
            val vars = vars.mapNotNull { it.toVar() }

            return if (conditions.isNotEmpty()) {
                Condition(
                    op = op,
                    parameters = Condition.Parameters.Conditions(conditions),
                )
            } else {
                Condition(
                    op = op,
                    parameters = Condition.Parameters.Vars(vars)
                )
            }
        }

        return null
    }

    fun toVar(): Var? {
        if (type != null && value != null) {
            return Var(
                type = type,
                value = value
            )
        }

        return null
    }
}
