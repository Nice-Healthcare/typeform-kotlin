package com.typeform.schema

sealed class FieldProperties {
    companion object {
    }

    data class DateStampProperties(val properties: DateStamp) : FieldProperties()
    data class DropdownProperties(val properties: Dropdown) : FieldProperties()
    data class GroupProperties(val properties: Group) : FieldProperties()
    data class LongTextProperties(val properties: LongText) : FieldProperties()
    data class MultipleChoiceProperties(val properties: MultipleChoice) : FieldProperties()
    data class NumberProperties(val properties: Number) : FieldProperties()
    data class RatingProperties(val properties: Rating) : FieldProperties()
    data class ShortTextProperties(val properties: ShortText) : FieldProperties()
    data class StatementProperties(val properties: Statement) : FieldProperties()
    data class YesNoProperties(val properties: YesNo) : FieldProperties()

    fun asDateStamp(): DateStamp? {
        return when (this) {
            is DateStampProperties -> {
                this.properties
            }
            else -> {
                null
            }
        }
    }

    fun asDropdown(): Dropdown? {
        return when (this) {
            is DropdownProperties -> {
                this.properties
            }
            else -> {
                null
            }
        }
    }

    fun asGroup(): Group? {
        return when (this) {
            is GroupProperties -> {
                this.properties
            }
            else -> {
                null
            }
        }
    }

    fun asLongText(): LongText? {
        return when (this) {
            is LongTextProperties -> {
                this.properties
            }
            else -> {
                null
            }
        }
    }

    fun asMultipleChoice(): MultipleChoice? {
        return when (this) {
            is MultipleChoiceProperties -> {
                this.properties
            }
            else -> {
                null
            }
        }
    }

    fun asNumber(): Number? {
        return when (this) {
            is NumberProperties -> {
                this.properties
            }
            else -> {
                null
            }
        }
    }

    fun asRating(): Rating? {
        return when (this) {
            is RatingProperties -> {
                this.properties
            }
            else -> {
                null
            }
        }
    }

    fun asShortText(): ShortText? {
        return when (this) {
            is ShortTextProperties -> {
                this.properties
            }
            else -> {
                null
            }
        }
    }

    fun asStatement(): Statement? {
        return when (this) {
            is StatementProperties -> {
                this.properties
            }
            else -> {
                null
            }
        }
    }

    fun asYesNo(): YesNo? {
        return when (this) {
            is YesNoProperties -> {
                this.properties
            }
            else -> {
                null
            }
        }
    }
}
