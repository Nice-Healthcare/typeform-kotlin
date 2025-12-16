package com.typeform.schema.logic

import com.typeform.models.ResponseValue
import com.typeform.models.Responses
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.intOrNull

data class Var(
    val type: VarType,
    val value: Value,
) {
    @Serializable(with = Value.Serializer::class)
    sealed class Value {
        data class Bool(
            val value: Boolean,
        ) : Value()

        data class Integer(
            val value: Int,
        ) : Value()

        data class RefOrString(
            val value: String,
        ) : Value()

        private object Serializer : KSerializer<Value> {

            private val primitiveSerializer = JsonPrimitive.serializer()

            override val descriptor: SerialDescriptor
                get() = primitiveSerializer.descriptor

            override fun serialize(
                encoder: Encoder,
                value: Value,
            ) {
                when (value) {
                    is Bool -> {
                        primitiveSerializer.serialize(encoder, JsonPrimitive(value.value))
                    }
                    is Integer -> {
                        primitiveSerializer.serialize(encoder, JsonPrimitive(value.value))
                    }
                    is RefOrString -> {
                        primitiveSerializer.serialize(encoder, JsonPrimitive(value.value))
                    }
                }
            }

            override fun deserialize(decoder: Decoder): Var.Value {
                val primitive = primitiveSerializer.deserialize(decoder)

                primitive.booleanOrNull?.let {
                    return Bool(it)
                }

                primitive.intOrNull?.let {
                    return Integer(it)
                }

                primitive.contentOrNull?.let {
                    return RefOrString(it)
                }

                throw SerializationException("Unknown Var.Value Type")
            }
        }
    }
}

fun List<Var>.matchGiven(
    responses: Responses,
    op: Op,
): Boolean? {
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
                    try {
                        op.compareInt(response.value, valueVar.value.value)
                    } catch (_: Exception) {
                        null
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
                    try {
                        op.compareString(response.value, valueVar.value.value)
                    } catch (_: Exception) {
                        null
                    }
                }
                is ResponseValue.DateValue -> {
                    try {
                        op.compareDate(response.value, valueVar.value.value)
                    } catch (_: Exception) {
                        null
                    }
                }
                else -> {
                    null
                }
            }
        }
    }
}

fun List<Var>.compactMatchGiven(
    responses: Responses,
    op: Op,
): List<Boolean> {
    val match = matchGiven(responses, op) ?: return emptyList()
    return listOf(match)
}
