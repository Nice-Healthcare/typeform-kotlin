package com.typeform.schema

sealed class FieldProperties {
    data class DateStampProperties(val properties: DateStamp) : FieldProperties()

    data class DropdownProperties(val properties: Dropdown) : FieldProperties()

    data class FileUploadProperties(val properties: FileUpload) : FieldProperties()

    data class GroupProperties(val properties: Group) : FieldProperties()

    data class LongTextProperties(val properties: LongText) : FieldProperties()

    data class MultipleChoiceProperties(val properties: MultipleChoice) : FieldProperties()

    data class NumberProperties(val properties: Number) : FieldProperties()

    data class OpinionScaleProperties(val properties: OpinionScale) : FieldProperties()

    data class RatingProperties(val properties: Rating) : FieldProperties()

    data class ShortTextProperties(val properties: ShortText) : FieldProperties()

    data class StatementProperties(val properties: Statement) : FieldProperties()

    data class YesNoProperties(val properties: YesNo) : FieldProperties()

    companion object {
    }

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

    fun asFileUpload(): FileUpload? {
        return when (this) {
            is FileUploadProperties -> {
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

    fun asOpinionScale(): OpinionScale? {
        return when (this) {
            is OpinionScaleProperties -> {
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

    val description: String?
        get() = when (this) {
            is DateStampProperties -> {
                this.properties.description
            }
            is DropdownProperties -> {
                this.properties.description
            }
            is FileUploadProperties -> {
                this.properties.description
            }
            is LongTextProperties -> {
                this.properties.description
            }
            is MultipleChoiceProperties -> {
                this.properties.description
            }
            is NumberProperties -> {
                this.properties.description
            }
            is RatingProperties -> {
                this.properties.description
            }
            is StatementProperties -> {
                this.properties.description
            }
            is YesNoProperties -> {
                this.properties.description
            }
            else -> {
                null
            }
        }

    val separator: String?
        get() = when (this) {
            is DateStampProperties -> {
                this.properties.separator
            }
            else -> {
                null
            }
        }

    val structure: String?
        get() = when (this) {
            is DateStampProperties -> {
                this.properties.structure
            }
            else -> {
                null
            }
        }

    val shape: String?
        get() = when (this) {
            is RatingProperties -> {
                this.properties.shape
            }
            else -> {
                null
            }
        }

    val steps: Int?
        get() = when (this) {
            is RatingProperties -> {
                this.properties.steps
            }
            is OpinionScaleProperties -> {
                this.properties.steps
            }
            else -> {
                null
            }
        }

    val labels: OpinionScale.Labels?
        get() = when (this) {
            is OpinionScaleProperties -> {
                this.properties.labels
            }
            else -> {
                null
            }
        }

    val startAtOne: Boolean?
        get() = when (this) {
            is OpinionScaleProperties -> {
                this.properties.start_at_one
            }
            else -> {
                null
            }
        }

    val choices: List<Choice>?
        get() = when (this) {
            is DropdownProperties -> {
                this.properties.choices
            }
            is MultipleChoiceProperties -> {
                this.properties.choices
            }
            else -> {
                null
            }
        }

    val randomize: Boolean?
        get() = when (this) {
            is DropdownProperties -> {
                this.properties.randomize
            }
            is MultipleChoiceProperties -> {
                this.properties.randomize
            }
            else -> {
                null
            }
        }

    val alphabeticalOrder: Boolean?
        get() = when (this) {
            is DropdownProperties -> {
                this.properties.alphabetical_order
            }
            else -> {
                null
            }
        }

    val allowMultipleSelection: Boolean?
        get() = when (this) {
            is MultipleChoiceProperties -> {
                this.properties.allow_multiple_selection
            }
            else -> {
                null
            }
        }

    val allowOtherChoice: Boolean?
        get() = when (this) {
            is MultipleChoiceProperties -> {
                this.properties.allow_other_choice
            }
            else -> {
                null
            }
        }

    val verticalAlignment: Boolean?
        get() = when (this) {
            is MultipleChoiceProperties -> {
                this.properties.vertical_alignment
            }
            else -> {
                null
            }
        }

    val buttonText: String?
        get() = when (this) {
            is StatementProperties -> {
                this.properties.button_text
            }
            else -> {
                null
            }
        }

    val fields: List<Field>?
        get() = when (this) {
            is GroupProperties -> {
                this.properties.fields
            }
            else -> {
                null
            }
        }

    val showButton: Boolean?
        get() = when (this) {
            is GroupProperties -> {
                this.properties.show_button
            }
            else -> {
                null
            }
        }

    val hideMarks: Boolean?
        get() = when (this) {
            is StatementProperties -> {
                this.properties.hide_marks
            }
            else -> {
                null
            }
        }
}
