package com.typeform.serializers

import com.typeform.contracts.FieldContract
import com.typeform.schema.Field
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object FieldSerializer : KSerializer<Field> {

    private val serializer = FieldContract.serializer()

    override val descriptor: SerialDescriptor
        get() = serializer.descriptor

    override fun serialize(
        encoder: Encoder,
        value: Field,
    ) {
        serializer.serialize(encoder, FieldContract(value))
    }

    override fun deserialize(decoder: Decoder): Field {
        return serializer.deserialize(decoder).toField()
    }
}
