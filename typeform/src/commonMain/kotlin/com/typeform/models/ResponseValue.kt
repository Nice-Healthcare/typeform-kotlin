package com.typeform.models

import com.typeform.schema.Choice
import com.typeform.serializers.ResponseValueSerializer
import java.util.Date
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toKotlinInstant
import kotlinx.serialization.Serializable

@Serializable(with = ResponseValueSerializer::class)
sealed class ResponseValue {
    data class BooleanValue(val value: Boolean) : ResponseValue()

    data class ChoiceValue(val value: Choice) : ResponseValue()

    data class ChoicesValue(val value: List<Choice>) : ResponseValue()

    @Deprecated(replaceWith = ReplaceWith("InstantValue"), message = "Use kotlinx-datetime")
    data class DateValue(val value: Date) : ResponseValue()

    data class InstantValue(val value: Instant) : ResponseValue()

    data class IntValue(val value: Int) : ResponseValue()

    data class StringValue(val value: String) : ResponseValue()

    data class UploadValue(val value: Upload) : ResponseValue()

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
            is ChoiceValue -> {
                listOf(this.value)
            }
            else -> {
                null
            }
        }
    }

    @Deprecated(replaceWith = ReplaceWith("asInstant()"), message = "Use kotlinx-datetime")
    fun asDate(): Date? {
        return when (this) {
            is DateValue -> {
                this.value
            }
            is InstantValue -> {
                Date.from(this.value.toJavaInstant())
            }
            else -> {
                null
            }
        }
    }

    fun asInstant(): Instant? {
        return when (this) {
            is DateValue -> {
                this.value.toInstant().toKotlinInstant()
            }
            is InstantValue -> {
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
}
