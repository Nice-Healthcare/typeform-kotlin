package com.typeform.serializers

import com.typeform.schema.LogicType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object LogicTypeSerializer : KSerializer<LogicType> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("LogicType", PrimitiveKind.STRING)

    override fun serialize(
        encoder: Encoder,
        value: LogicType,
    ) {
        encoder.encodeString(value.rawValue)
    }

    override fun deserialize(decoder: Decoder): LogicType {
        return LogicType.fromRawValue(decoder.decodeString())
    }
}
