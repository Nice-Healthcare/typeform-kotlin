package com.typeform.serializers

import com.typeform.schema.Op
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object OpSerializer : KSerializer<Op> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("Op", PrimitiveKind.STRING)

    override fun serialize(
        encoder: Encoder,
        value: Op,
    ) {
        encoder.encodeString(value.rawValue)
    }

    override fun deserialize(decoder: Decoder): Op {
        val rawValue = decoder.decodeString()
        return Op.entries.firstOrNull { it.rawValue == rawValue }
            ?: throw SerializationException("Unhandled 'Op' value '$rawValue'.")
    }
}
