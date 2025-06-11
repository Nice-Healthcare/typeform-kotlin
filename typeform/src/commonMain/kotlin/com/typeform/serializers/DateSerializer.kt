package com.typeform.serializers

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object DateSerializer : KSerializer<Date> {

    /**
     * Formatter that uses the format **2023-03-57T10:30:23+00:00**.
     */
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())

    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("Date", PrimitiveKind.STRING)

    override fun serialize(
        encoder: Encoder,
        value: Date,
    ) {
        encoder.encodeString(dateFormat.format(value))
    }

    override fun deserialize(decoder: Decoder): Date {
        val rawValue = decoder.decodeString()
        return dateFormat.parse(rawValue)
            ?: throw SerializationException("Unhandled 'Date' value '$rawValue'.")
    }
}
