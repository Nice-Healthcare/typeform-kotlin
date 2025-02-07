package com.typeform.serializers

import com.typeform.schema.ActionType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object ActionTypeSerializer : KSerializer<ActionType> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("ActionType", PrimitiveKind.STRING)

    override fun serialize(
        encoder: Encoder,
        value: ActionType,
    ) {
        encoder.encodeString(value.rawValue)
    }

    override fun deserialize(decoder: Decoder): ActionType {
        val rawValue = decoder.decodeString()
        return ActionType.entries.firstOrNull { it.rawValue == rawValue }
            ?: throw SerializationException("Unhandled 'ActionType' value '$rawValue'.")
    }
}
