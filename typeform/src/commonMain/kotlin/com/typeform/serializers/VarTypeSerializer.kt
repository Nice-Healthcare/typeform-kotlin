package com.typeform.serializers

import com.typeform.schema.VarType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object VarTypeSerializer : KSerializer<VarType> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("VarType", PrimitiveKind.STRING)

    override fun serialize(
        encoder: Encoder,
        value: VarType,
    ) {
        encoder.encodeString(value.rawValue)
    }

    override fun deserialize(decoder: Decoder): VarType {
        val rawValue = decoder.decodeString()
        return VarType.entries.firstOrNull { it.rawValue == rawValue }
            ?: throw SerializationException("Unhandled 'VarType' value '$rawValue'.")
    }
}
