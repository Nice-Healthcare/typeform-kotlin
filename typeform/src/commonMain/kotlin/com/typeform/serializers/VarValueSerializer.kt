package com.typeform.serializers

import com.typeform.schema.Var
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.intOrNull

object VarValueSerializer : KSerializer<Var.Value> {

    private val serializer = JsonPrimitive.serializer()

    override val descriptor: SerialDescriptor
        get() = serializer.descriptor

    override fun serialize(encoder: Encoder, value: Var.Value) {
        when (value) {
            is Var.Value.Bool -> {
                serializer.serialize(encoder, JsonPrimitive(value.value))
            }
            is Var.Value.Integer -> {
                serializer.serialize(encoder, JsonPrimitive(value.value))
            }
            is Var.Value.RefOrString -> {
                serializer.serialize(encoder, JsonPrimitive(value.value))
            }
        }
    }

    override fun deserialize(decoder: Decoder): Var.Value {
        val primitive = serializer.deserialize(decoder)

        primitive.booleanOrNull?.let {
            return Var.Value.Bool(it)
        }

        primitive.intOrNull?.let {
            return Var.Value.Integer(it)
        }

        primitive.contentOrNull?.let {
            return Var.Value.RefOrString(it)
        }

        throw SerializationException("Unknown Var.Value Type")
    }
}