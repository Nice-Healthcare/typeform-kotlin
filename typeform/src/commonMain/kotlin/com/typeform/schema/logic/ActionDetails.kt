package com.typeform.schema.logic

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
data class ActionDetails(
    val to: To,
) {
    @Serializable
    data class To(
        val type: ToType,
        val value: String,
    )

    @Serializable(with = ToType.Serializer::class)
    enum class ToType(
        val rawValue: String,
    ) {
        FIELD("field"),
        THANK_YOU("thankyou"),
        ;

        private object Serializer : KSerializer<ToType> {
            override val descriptor: SerialDescriptor
                get() = PrimitiveSerialDescriptor("ActionDetails.ToType", PrimitiveKind.STRING)

            override fun serialize(
                encoder: Encoder,
                value: ToType,
            ) {
                encoder.encodeString(value.rawValue)
            }

            override fun deserialize(decoder: Decoder): ToType {
                val rawValue = decoder.decodeString()
                return ToType.entries.firstOrNull { it.rawValue == rawValue }
                    ?: throw SerializationException("Unhandled 'ActionDetails.ToType' value '$rawValue'.")
            }
        }
    }
}
