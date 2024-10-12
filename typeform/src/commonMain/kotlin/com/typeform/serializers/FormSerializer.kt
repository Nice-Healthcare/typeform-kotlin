package com.typeform.serializers

import com.typeform.contracts.FormContract
import com.typeform.schema.Form
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object FormSerializer : KSerializer<Form> {

    private val serializer = FormContract.serializer()

    override val descriptor: SerialDescriptor
        get() = serializer.descriptor

    override fun serialize(encoder: Encoder, value: Form) {
        serializer.serialize(encoder, FormContract(value))
    }

    override fun deserialize(decoder: Decoder): Form {
        return serializer.deserialize(decoder).toForm()
    }
}