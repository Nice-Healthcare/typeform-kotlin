package com.typeform.schema.logic

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = ActionType.Serializer::class)
enum class ActionType(
    val rawValue: String,
) {
    JUMP("jump"),
    ;

    private object Serializer : KSerializer<ActionType> {
        override val descriptor: SerialDescriptor
            get() = PrimitiveSerialDescriptor("ActionType", PrimitiveKind.STRING)

        override fun serialize(
            encoder: Encoder,
            value: ActionType,
        ) {
            encoder.encodeString(value.rawValue)
        }

        override fun deserialize(decoder: Decoder): ActionType {
            val rawValue = decoder.decodeString()
            return ActionType.entries.firstOrNull { it.rawValue == rawValue }
                ?: throw SerializationException("Unhandled 'ActionType' value '$rawValue'.")
        }
    }
}
