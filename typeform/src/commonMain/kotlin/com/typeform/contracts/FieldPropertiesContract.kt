package com.typeform.contracts

import com.typeform.schema.Choice
import com.typeform.schema.DateStamp
import com.typeform.schema.Dropdown
import com.typeform.schema.FieldProperties
import com.typeform.schema.FieldType
import com.typeform.schema.FileUpload
import com.typeform.schema.Group
import com.typeform.schema.LongText
import com.typeform.schema.MultipleChoice
import com.typeform.schema.Number
import com.typeform.schema.OpinionScale
import com.typeform.schema.Rating
import com.typeform.schema.ShortText
import com.typeform.schema.Statement
import com.typeform.schema.YesNo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FieldPropertiesContract(
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
    val fields: List<FieldContract>?,
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
        fields = fieldProperties.fields?.map { FieldContract(it) },
        showButton = fieldProperties.showButton,
        hideMarks = fieldProperties.hideMarks,
    )

    fun toFieldProperties(type: FieldType): FieldProperties {
        return when (type) {
            FieldType.DATE -> {
                FieldProperties.DateStampProperties(
                    DateStamp(
                        separator = separator ?: "",
                        structure = structure ?: "",
                        description = description,
                    ),
                )
            }
            FieldType.DROPDOWN -> {
                FieldProperties.DropdownProperties(
                    Dropdown(
                        choices = choices ?: emptyList(),
                        description = null,
                        randomize = randomize == true,
                        alphabetical_order = alphabeticalOrder == true,
                    ),
                )
            }
            FieldType.FILE_UPLOAD -> {
                FieldProperties.FileUploadProperties(
                    FileUpload(
                        description = null,
                    ),
                )
            }
            FieldType.GROUP -> {
                FieldProperties.GroupProperties(
                    Group(
                        button_text = buttonText ?: "",
                        fields = fields?.map { it.toField() } ?: emptyList(),
                        show_button = showButton == true,
                    ),
                )
            }
            FieldType.LONG_TEXT -> {
                FieldProperties.LongTextProperties(
                    LongText(
                        description = description,
                    ),
                )
            }
            FieldType.MULTIPLE_CHOICE -> {
                FieldProperties.MultipleChoiceProperties(
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
                FieldProperties.NumberProperties(
                    Number(
                        description = description,
                    ),
                )
            }
            FieldType.OPINION_SCALE -> {
                FieldProperties.OpinionScaleProperties(
                    OpinionScale(
                        steps = steps ?: 0,
                        labels = labels ?: OpinionScale.Labels(),
                        start_at_one = startAtOne == true,
                    ),
                )
            }
            FieldType.RATING -> {
                FieldProperties.RatingProperties(
                    Rating(
                        shape = shape ?: "",
                        steps = steps ?: 0,
                        description = description,
                    ),
                )
            }
            FieldType.SHORT_TEXT -> {
                FieldProperties.ShortTextProperties(
                    ShortText(
                        description = description,
                    ),
                )
            }
            FieldType.STATEMENT -> {
                FieldProperties.StatementProperties(
                    Statement(
                        hide_marks = hideMarks == true,
                        button_text = buttonText ?: "",
                        description = description,
                    ),
                )
            }
            FieldType.YES_NO -> {
                FieldProperties.YesNoProperties(
                    YesNo(
                        description = description,
                    ),
                )
            }
        }
    }
}
