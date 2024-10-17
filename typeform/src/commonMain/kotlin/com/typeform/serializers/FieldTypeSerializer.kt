package com.typeform.serializers

import com.typeform.schema.FieldType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object FieldTypeSerializer : KSerializer<FieldType> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("FieldType", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: FieldType) {
        encoder.encodeString(value.rawValue)
    }

    override fun deserialize(decoder: Decoder): FieldType {
        return FieldType.fromRawValue(decoder.decodeString())
    }
}