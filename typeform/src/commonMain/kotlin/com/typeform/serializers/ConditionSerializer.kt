package com.typeform.serializers

import com.typeform.contracts.ConditionContract
import com.typeform.schema.Condition
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object ConditionSerializer : KSerializer<Condition> {

    private val serializer = ConditionContract.serializer()

    override val descriptor: SerialDescriptor
        get() = serializer.descriptor

    override fun serialize(encoder: Encoder, value: Condition) {
        serializer.serialize(encoder, ConditionContract(value))
    }

    override fun deserialize(decoder: Decoder): Condition {
        TODO("What's happening here?")
        return serializer.deserialize(decoder).toCondition() ?: Condition()
    }
}