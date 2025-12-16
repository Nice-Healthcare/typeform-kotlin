package com.typeform.schema.structure

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = FieldType.Serializer::class)
enum class FieldType(
    val rawValue: String,
) {
    DATE("date"),
    DROPDOWN("dropdown"),
    FILE_UPLOAD("file_upload"),
    GROUP("group"),
    LONG_TEXT("long_text"),
    MULTIPLE_CHOICE("multiple_choice"),
    NUMBER("number"),
    OPINION_SCALE("opinion_scale"),
    RATING("rating"),
    SHORT_TEXT("short_text"),
    STATEMENT("statement"),
    YES_NO("yes_no"),
    ;

    private object Serializer : KSerializer<FieldType> {
        override val descriptor: SerialDescriptor
            get() = PrimitiveSerialDescriptor("FieldType", PrimitiveKind.STRING)

        override fun serialize(
            encoder: Encoder,
            value: FieldType,
        ) {
            encoder.encodeString(value.rawValue)
        }

        override fun deserialize(decoder: Decoder): FieldType {
            val rawValue = decoder.decodeString()
            return FieldType.entries.firstOrNull { it.rawValue == rawValue }
                ?: throw SerializationException("Unhandled 'FieldType' value '$rawValue'.")
        }
    }
}
