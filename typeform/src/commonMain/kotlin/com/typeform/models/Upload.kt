package com.typeform.models

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
data class Upload(
    val bytes: ByteArray,
    val path: Path,
    val mimeType: String,
    val fileName: String,
) {
    @Serializable(with = Path.Serializer::class)
    enum class Path(
        val rawValue: String,
    ) {
        CAMERA("camera"),
        DOCUMENTS("documents"),
        PHOTO_LIBRARY("photoLibrary"),
        ;

        internal object Serializer : KSerializer<Path> {
            override val descriptor: SerialDescriptor
                get() = PrimitiveSerialDescriptor("Upload.Path", PrimitiveKind.STRING)

            override fun serialize(
                encoder: Encoder,
                value: Path,
            ) {
                encoder.encodeString(value.rawValue)
            }

            override fun deserialize(decoder: Decoder): Path {
                val rawValue = decoder.decodeString()
                return Path.entries.firstOrNull { it.rawValue == rawValue }
                    ?: throw SerializationException("Unhandled 'Upload.Path' value '$rawValue'.")
            }
        }
    }

    companion object
}
