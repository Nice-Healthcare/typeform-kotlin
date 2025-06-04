package com.typeform.serializers

import com.typeform.contracts.ResponseValueContract
import com.typeform.models.ResponseValue
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object ResponseValueSerializer : KSerializer<ResponseValue> {

    private val serializer = ResponseValueContract.serializer()

    override val descriptor: SerialDescriptor
        get() = serializer.descriptor

    override fun serialize(
        encoder: Encoder,
        value: ResponseValue,
    ) {
        serializer.serialize(encoder, ResponseValueContract(value))
    }

    override fun deserialize(decoder: Decoder): ResponseValue {
        val responseValue = serializer.deserialize(decoder).toResponseValue()
            ?: throw SerializationException("Unable to deserializer ResponseValue.")

        return responseValue
    }
}
