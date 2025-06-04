package com.typeform.serializers

import com.typeform.models.Upload
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object UploadPathSerializer : KSerializer<Upload.Path> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("Upload.Path", PrimitiveKind.STRING)

    override fun serialize(
        encoder: Encoder,
        value: Upload.Path,
    ) {
        encoder.encodeString(value.rawValue)
    }

    override fun deserialize(decoder: Decoder): Upload.Path {
        val rawValue = decoder.decodeString()
        return Upload.Path.entries.firstOrNull { it.rawValue == rawValue }
            ?: throw SerializationException("Unhandled 'Upload.Path' value '$rawValue'.")
    }
}
