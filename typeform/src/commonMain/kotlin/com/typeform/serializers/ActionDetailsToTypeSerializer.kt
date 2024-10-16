package com.typeform.serializers

import com.typeform.schema.ActionDetails
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object ActionDetailsToTypeSerializer : KSerializer<ActionDetails.ToType> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("ActionDetails.ToType", PrimitiveKind.STRING)

    override fun serialize(
        encoder: Encoder,
        value: ActionDetails.ToType,
    ) {
        encoder.encodeString(value.rawValue)
    }

    override fun deserialize(decoder: Decoder): ActionDetails.ToType {
        return ActionDetails.ToType.fromRawValue(decoder.decodeString())
    }
}
