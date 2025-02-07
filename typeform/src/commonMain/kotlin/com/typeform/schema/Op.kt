package com.typeform.schema

import com.typeform.models.TypeformException
import com.typeform.serializers.OpSerializer
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlinx.serialization.Serializable

@Serializable(with = OpSerializer::class)
enum class Op(val rawValue: String) {
    ALWAYS("always"),
    AND("and"),
    BEGINS_WITH("begins_with"),
    CONTAINS("contains"),
    EARLIER_THAN("earlier_than"),
    EARLIER_THAN_OR_ON("earlier_than_or_on"),
    ENDS_WITH("ends_with"),
    EQUAL("equal"),
    GREATER_EQUAL_THAN("greater_equal_than"),
    GREATER_THAN("greater_than"),
    IS("is"),
    IS_NOT("is_not"),
    LATER_THAN("later_than"),
    LATER_THAN_OR_ON("later_than_or_on"),
    LOWER_EQUAL_THAN("lower_equal_than"),
    LOWER_THAN("lower_than"),
    NOT_CONTAINS("not_contains"),
    NOT_ON("not_on"),
    ON("on"),
    OR("or"),
    ;

    companion object {
        private var yearMonthDay = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    }

    @Throws(TypeformException.UnexpectedOperation::class)
    fun compareString(
        response: String,
        condition: String,
    ): Boolean {
        when (this) {
            BEGINS_WITH -> {
                return response.startsWith(condition)
            }
            CONTAINS -> {
                return response.contains(condition)
            }
            ENDS_WITH -> {
                return response.endsWith(condition)
            }
            EQUAL -> {
                return response == condition
            }
            NOT_CONTAINS -> {
                return !response.contains(condition)
            }
            else -> {
                throw TypeformException.UnexpectedOperation(this)
            }
        }
    }

    @Throws(TypeformException.UnexpectedOperation::class)
    fun compareInt(
        response: Int,
        condition: Int,
    ): Boolean {
        when (this) {
            EQUAL -> {
                return response == condition
            }
            GREATER_EQUAL_THAN -> {
                return response >= condition
            }
            GREATER_THAN -> {
                return response > condition
            }
            LOWER_EQUAL_THAN -> {
                return response <= condition
            }
            LOWER_THAN -> {
                return response < condition
            }
            else -> {
                throw TypeformException.UnexpectedOperation(this)
            }
        }
    }

    @Throws(TypeformException::class)
    fun compareDate(
        response: Date,
        condition: String,
    ): Boolean {
        val conditionDate = yearMonthDay.parse(condition)
            ?: throw TypeformException.ResponseTypeMismatch(Date::class.simpleName ?: "Date")

        val comparison = response.compareTo(conditionDate)

        when (this) {
            EARLIER_THAN -> {
                return comparison < 0
            }
            EARLIER_THAN_OR_ON -> {
                return comparison <= 0
            }
            LATER_THAN -> {
                return comparison > 0
            }
            LATER_THAN_OR_ON -> {
                return comparison >= 0
            }
            NOT_ON -> {
                return comparison != 0
            }
            ON -> {
                return comparison == 0
            }
            else -> {
                throw TypeformException.UnexpectedOperation(this)
            }
        }
    }
}
