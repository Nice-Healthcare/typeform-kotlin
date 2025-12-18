package com.typeform.schema.structure

import com.typeform.schema.questions.DateStamp
import com.typeform.schema.questions.Dropdown
import com.typeform.schema.questions.FileUpload
import com.typeform.schema.questions.LongText
import com.typeform.schema.questions.MultipleChoice
import com.typeform.schema.questions.Number
import com.typeform.schema.questions.OpinionScale
import com.typeform.schema.questions.Rating
import com.typeform.schema.questions.ShortText
import com.typeform.schema.questions.YesNo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed class FieldProperties {
    data class DateStampProperties(
        val properties: DateStamp,
    ) : FieldProperties()

    data class DropdownProperties(
        val properties: Dropdown,
    ) : FieldProperties()

    data class FileUploadProperties(
        val properties: FileUpload,
    ) : FieldProperties()

    data class GroupProperties(
        val properties: Group,
    ) : FieldProperties()

    data class LongTextProperties(
        val properties: LongText,
    ) : FieldProperties()

    data class MultipleChoiceProperties(
        val properties: MultipleChoice,
    ) : FieldProperties()

    data class NumberProperties(
        val properties: Number,
    ) : FieldProperties()

    data class OpinionScaleProperties(
        val properties: OpinionScale,
    ) : FieldProperties()

    data class RatingProperties(
        val properties: Rating,
    ) : FieldProperties()

    data class ShortTextProperties(
        val properties: ShortText,
    ) : FieldProperties()

    data class StatementProperties(
        val properties: Statement,
    ) : FieldProperties()

    data class YesNoProperties(
        val properties: YesNo,
    ) : FieldProperties()

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
            is ShortTextProperties -> {
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

    @Serializable
    internal data class Contract(
        val description: String?,
        // Datestamp
        val separator: String?,
        val structure: String?,
        // Rating
        val shape: String?,
        // Rating, Opinion Scale
        val steps: Int?,
        // Opinion Scale
        val labels: OpinionScale.Labels?,
        @SerialName("start_at_one")
        val startAtOne: Boolean?,
        // Dropdown, Multiple Choice
        val choices: List<Choice>?,
        val randomize: Boolean?,
        // Dropdown
        @SerialName("alphabetical_order")
        val alphabeticalOrder: Boolean?,
        // Multiple Choice
        @SerialName("allow_multiple_selection")
        val allowMultipleSelection: Boolean?,
        @SerialName("allow_other_choice")
        val allowOtherChoice: Boolean?,
        @SerialName("vertical_alignment")
        val verticalAlignment: Boolean?,
        // Group, Statement
        @SerialName("button_text")
        val buttonText: String?,
        // Group
        val fields: List<Field.Contract>?,
        @SerialName("show_button")
        val showButton: Boolean?,
        // Statement
        @SerialName("hide_marks")
        val hideMarks: Boolean?,
    ) {
        constructor(fieldProperties: FieldProperties) : this(
            description = fieldProperties.description,
            separator = fieldProperties.separator,
            structure = fieldProperties.structure,
            shape = fieldProperties.shape,
            steps = fieldProperties.steps,
            labels = fieldProperties.labels,
            startAtOne = fieldProperties.startAtOne,
            choices = fieldProperties.choices,
            randomize = fieldProperties.randomize,
            alphabeticalOrder = fieldProperties.alphabeticalOrder,
            allowMultipleSelection = fieldProperties.allowMultipleSelection,
            allowOtherChoice = fieldProperties.allowOtherChoice,
            verticalAlignment = fieldProperties.verticalAlignment,
            buttonText = fieldProperties.buttonText,
            fields = fieldProperties.fields?.map { Field.Contract(it) },
            showButton = fieldProperties.showButton,
            hideMarks = fieldProperties.hideMarks,
        )

        fun toFieldProperties(type: FieldType): FieldProperties {
            return when (type) {
                FieldType.DATE -> {
                    DateStampProperties(
                        DateStamp(
                            separator = separator ?: "",
                            structure = structure ?: "",
                            description = description,
                        ),
                    )
                }
                FieldType.DROPDOWN -> {
                    DropdownProperties(
                        Dropdown(
                            choices = choices ?: emptyList(),
                            description = null,
                            randomize = randomize == true,
                            alphabetical_order = alphabeticalOrder == true,
                        ),
                    )
                }
                FieldType.FILE_UPLOAD -> {
                    FileUploadProperties(
                        FileUpload(
                            description = null,
                        ),
                    )
                }
                FieldType.GROUP -> {
                    GroupProperties(
                        Group(
                            button_text = buttonText ?: "",
                            fields = fields?.map { it.toField() } ?: emptyList(),
                            show_button = showButton == true,
                        ),
                    )
                }
                FieldType.LONG_TEXT -> {
                    LongTextProperties(
                        LongText(
                            description = description,
                        ),
                    )
                }
                FieldType.MULTIPLE_CHOICE -> {
                    MultipleChoiceProperties(
                        MultipleChoice(
                            choices = choices ?: emptyList(),
                            randomize = randomize == true,
                            allow_multiple_selection = allowMultipleSelection == true,
                            allow_other_choice = allowOtherChoice == true,
                            vertical_alignment = verticalAlignment == true,
                            description = description,
                        ),
                    )
                }
                FieldType.NUMBER -> {
                    NumberProperties(
                        Number(
                            description = description,
                        ),
                    )
                }
                FieldType.OPINION_SCALE -> {
                    OpinionScaleProperties(
                        OpinionScale(
                            steps = steps ?: 0,
                            labels = labels ?: OpinionScale.Labels(),
                            start_at_one = startAtOne == true,
                        ),
                    )
                }
                FieldType.RATING -> {
                    RatingProperties(
                        Rating(
                            shape = shape ?: "",
                            steps = steps ?: 0,
                            description = description,
                        ),
                    )
                }
                FieldType.SHORT_TEXT -> {
                    ShortTextProperties(
                        ShortText(
                            description = description,
                        ),
                    )
                }
                FieldType.STATEMENT -> {
                    StatementProperties(
                        Statement(
                            hide_marks = hideMarks == true,
                            button_text = buttonText ?: "",
                            description = description,
                        ),
                    )
                }
                FieldType.YES_NO -> {
                    YesNoProperties(
                        YesNo(
                            description = description,
                        ),
                    )
                }
            }
        }
    }
}
