package com.typeform.schema

import com.typeform.models.ResponseValue
import com.typeform.models.Responses

data class Var(
    val type: VarType,
    val value: Value,
) {
    sealed class Value {
        companion object {
        }

        data class Bool(val value: Boolean) : Value()
        data class Integer(val value: Int) : Value()
        data class RefOrString(val value: String) : Value()
    }
}

fun List<Var>.matchGiven(responses: Responses, op: Op): Boolean? {
    if (isEmpty()) {
        return true
    }

    val fieldVar = firstOrNull { it.type == VarType.FIELD } ?: return null
    val fieldRef = when (fieldVar.value) {
        is Var.Value.RefOrString -> {
            fieldVar.value.value
        }
        else -> {
            return null
        }
    }

    val valueVar = firstOrNull { it.type != VarType.FIELD } ?: return null
    val response = responses[fieldRef] ?: return null

    when (valueVar.value) {
        is Var.Value.Bool -> {
            return when (response) {
                is ResponseValue.BooleanValue -> {
                    response.value == valueVar.value.value
                }
                else -> {
                    null
                }
            }
        }
        is Var.Value.Integer -> {
            return when (response) {
                is ResponseValue.IntValue -> {
                    when (op) {
                        Op.EQUAL -> {
                            response.value == valueVar.value.value
                        }
                        Op.GREATER_EQUAL_THAN -> {
                            response.value >= valueVar.value.value
                        }
                        Op.GREATER_THAN -> {
                            response.value > valueVar.value.value
                        }
                        Op.LOWER_EQUAL_THAN -> {
                            response.value <= valueVar.value.value
                        }
                        Op.LOWER_THAN -> {
                            response.value < valueVar.value.value
                        }
                        else -> {
                            null
                        }
                    }
                }
                else -> {
                    null
                }
            }
        }
        is Var.Value.RefOrString -> {
            return when (response) {
                is ResponseValue.ChoiceValue -> {
                    response.value.ref == valueVar.value.value
                }
                is ResponseValue.ChoicesValue -> {
                    response.value.map { it.ref }.contains(valueVar.value.value)
                }
                is ResponseValue.StringValue -> {
                    response.value == valueVar.value.value
                }
                else -> {
                    null
                }
            }
        }
    }
}

fun List<Var>.compactMatchGiven(responses: Responses, op: Op): List<Boolean> {
    val match = matchGiven(responses, op) ?: return emptyList()
    return listOf(match)
}
