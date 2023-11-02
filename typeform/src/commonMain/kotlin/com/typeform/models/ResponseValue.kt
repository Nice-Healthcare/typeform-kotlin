package com.typeform.models

import com.typeform.schema.Choice
import java.util.Date

sealed class ResponseValue {
    data class BooleanValue(val value: Boolean) : ResponseValue()
    data class ChoiceValue(val value: Choice) : ResponseValue()
    data class ChoicesValue(val value: List<Choice>) : ResponseValue()
    data class DateValue(val value: Date) : ResponseValue()
    data class IntValue(val value: Int) : ResponseValue()
    data class StringValue(val value: String) : ResponseValue()

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
}
