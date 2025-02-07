package com.typeform.serializers

import com.typeform.schema.FormType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object FormTypeSerializer : KSerializer<FormType> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("FormType", PrimitiveKind.STRING)

    override fun serialize(
        encoder: Encoder,
        value: FormType,
    ) {
        encoder.encodeString(value.rawValue)
    }

    override fun deserialize(decoder: Decoder): FormType {
        val rawValue = decoder.decodeString()
        return FormType.entries.firstOrNull { it.rawValue == rawValue }
            ?: throw SerializationException("Unhandled 'FormType' value '$rawValue'.")
    }
}
