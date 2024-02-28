package com.typeform.schema

import com.typeform.models.Responses

data class Condition(
    val op: Op = Op.ALWAYS,
    val parameters: Parameters = Parameters.Vars(emptyList()),
) {
    companion object {
    }

    sealed class Parameters {
        companion object {
        }

        data class Vars(val vars: List<Var>) : Parameters()
        data class Conditions(val conditions: List<Condition>) : Parameters()
    }

    fun satisfiedGiven(responses: Responses): Boolean? {
        val satisfied = when (parameters) {
            is Parameters.Vars -> {
                parameters.vars.compactMatchGiven(responses, op)
            }
            is Parameters.Conditions -> {
                parameters.conditions.mapNotNull { it.satisfiedGiven(responses) }
            }
        }

        if (op == Op.ALWAYS) {
            return true
        }

        if (satisfied.isEmpty()) {
            return false
        }

        return when (op) {
            Op.AND -> {
                !satisfied.contains(false)
            }
            Op.OR -> {
                satisfied.contains(true)
            }
            Op.IS -> {
                !satisfied.contains(false)
            }
            Op.IS_NOT -> {
                !satisfied.contains(true)
            }
            else -> {
                null
            }
        }
    }
}
