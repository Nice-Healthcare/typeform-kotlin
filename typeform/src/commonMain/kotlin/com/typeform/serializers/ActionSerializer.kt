package com.typeform.serializers

import com.typeform.contracts.ActionContract
import com.typeform.schema.Action
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object ActionSerializer : KSerializer<Action> {

    private val serializer = ActionContract.serializer()

    override val descriptor: SerialDescriptor
        get() = serializer.descriptor

    override fun serialize(encoder: Encoder, value: Action) {
        serializer.serialize(encoder, ActionContract(value))
    }

    override fun deserialize(decoder: Decoder): Action {
        return serializer.deserialize(decoder).toAction()
    }
}