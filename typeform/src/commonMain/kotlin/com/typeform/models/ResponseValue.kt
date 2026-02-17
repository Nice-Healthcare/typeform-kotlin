package com.typeform.models

import com.typeform.schema.structure.Choice
import com.typeform.serializers.DateSerializer
import java.util.Date
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = ResponseValue.Serializer::class)
sealed class ResponseValue {
    data class BooleanValue(
        val value: Boolean,
    ) : ResponseValue()

    data class ChoiceValue(
        val value: Choice,
    ) : ResponseValue()

    data class ChoicesValue(
        val value: List<Choice>,
    ) : ResponseValue()

    data class ChoicesByReferenceValue(
        val value: Map<String, List<Choice>>,
    ) : ResponseValue()

    data class DateValue(
        val value: Date,
    ) : ResponseValue()

    data class IntValue(
        val value: Int,
    ) : ResponseValue()

    data class StringValue(
        val value: String,
    ) : ResponseValue()

    data class UploadValue(
        val value: Upload,
    ) : ResponseValue()

    companion object {
        private val serializer = Contract.serializer()
    }

    fun asBoolean(): Boolean? {
        return when (this) {
            is BooleanValue -> {
                this.value
            }
            else -> {
                null
            }
        }
    }

    fun asChoice(): Choice? {
        return when (this) {
            is ChoiceValue -> {
                this.value
            }
            else -> {
                null
            }
        }
    }

    fun asChoices(): List<Choice>? {
        return when (this) {
            is ChoicesValue -> {
                this.value
            }
            else -> {
                null
            }
        }
    }

    fun asChoicesByReference(): Map<String, List<Choice>>? {
        return when (this) {
            is ChoicesByReferenceValue -> {
                this.value
            }
            else -> {
                null
            }
        }
    }

    fun asDate(): Date? {
        return when (this) {
            is DateValue -> {
                this.value
            }
            else -> {
                null
            }
        }
    }

    fun asInt(): Int? {
        return when (this) {
            is IntValue -> {
                this.value
            }
            else -> {
                null
            }
        }
    }

    fun asString(): String? {
        return when (this) {
            is StringValue -> {
                this.value
            }
            else -> {
                null
            }
        }
    }

    fun asUpload(): Upload? {
        return when (this) {
            is UploadValue -> {
                this.value
            }
            else -> {
                null
            }
        }
    }

    @Serializable
    internal data class Contract(
        val bool: Boolean?,
        val choice: Choice?,
        val choices: List<Choice>?,
        val choicesByReference: Map<String, List<Choice>>?,
        @Serializable(with = DateSerializer::class)
        val date: Date?,
        val int: Int?,
        val string: String?,
        val upload: Upload?,
    ) {
        constructor(responseValue: ResponseValue) : this(
            bool = responseValue.asBoolean(),
            choice = responseValue.asChoice(),
            choices = responseValue.asChoices(),
            choicesByReference = responseValue.asChoicesByReference(),
            date = responseValue.asDate(),
            int = responseValue.asInt(),
            string = responseValue.asString(),
            upload = responseValue.asUpload(),
        )

        fun toResponseValue(): ResponseValue? {
            return if (bool != null) {
                ResponseValue.BooleanValue(bool)
            } else if (choice != null) {
                ResponseValue.ChoiceValue(choice)
            } else if (choices != null) {
                ResponseValue.ChoicesValue(choices)
            } else if (choicesByReference != null) {
                ResponseValue.ChoicesByReferenceValue(choicesByReference)
            } else if (date != null) {
                ResponseValue.DateValue(date)
            } else if (int != null) {
                ResponseValue.IntValue(int)
            } else if (string != null) {
                ResponseValue.StringValue(string)
            } else if (upload != null) {
                ResponseValue.UploadValue(upload)
            } else {
                null
            }
        }
    }

    private object Serializer : KSerializer<ResponseValue> {
        override val descriptor: SerialDescriptor
            get() = serializer.descriptor

        override fun serialize(
            encoder: Encoder,
            value: ResponseValue,
        ) {
            serializer.serialize(encoder, Contract(value))
        }

        override fun deserialize(decoder: Decoder): ResponseValue {
            val responseValue = serializer.deserialize(decoder).toResponseValue()
                ?: throw SerializationException("Unable to deserializer ResponseValue.")

            return responseValue
        }
    }
}
