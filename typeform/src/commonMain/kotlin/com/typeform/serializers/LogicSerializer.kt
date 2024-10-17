package com.typeform.serializers

import com.typeform.contracts.LogicContract
import com.typeform.schema.Logic
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object LogicSerializer : KSerializer<Logic> {

    private val serializer = LogicContract.serializer()

    override val descriptor: SerialDescriptor
        get() = serializer.descriptor

    override fun serialize(
        encoder: Encoder,
        value: Logic,
    ) {
        serializer.serialize(encoder, LogicContract(value))
    }

    override fun deserialize(decoder: Decoder): Logic {
        return serializer.deserialize(decoder).toLogic()
    }
}
