package com.typeform.serializers

import com.typeform.schema.structure.AttachmentType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object AttachmentTypeSerializer : KSerializer<AttachmentType> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("AttachmentType", PrimitiveKind.STRING)

    override fun serialize(
        encoder: Encoder,
        value: AttachmentType,
    ) {
        encoder.encodeString(value.rawValue)
    }

    override fun deserialize(decoder: Decoder): AttachmentType {
        val rawValue = decoder.decodeString()
        return AttachmentType.entries.firstOrNull { it.rawValue == rawValue }
            ?: throw SerializationException("Unhandled 'AttachmentType' value '$rawValue'.")
    }
}
