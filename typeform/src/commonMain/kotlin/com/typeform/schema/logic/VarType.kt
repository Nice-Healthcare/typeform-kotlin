package com.typeform.schema.logic

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = VarType.Serializer::class)
enum class VarType(
    val rawValue: String,
) {
    CHOICE("choice"),
    CONSTANT("constant"),
    FIELD("field"),
    ;

    private object Serializer : KSerializer<VarType> {
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
}
