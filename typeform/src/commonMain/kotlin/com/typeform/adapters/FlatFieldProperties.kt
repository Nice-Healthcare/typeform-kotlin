package com.typeform.adapters

import com.typeform.schema.Choice
import com.typeform.schema.DateStamp
import com.typeform.schema.Dropdown
import com.typeform.schema.Field
import com.typeform.schema.FieldProperties
import com.typeform.schema.FieldType
import com.typeform.schema.Group
import com.typeform.schema.LongText
import com.typeform.schema.MultipleChoice
import com.typeform.schema.OpinionScale
import com.typeform.schema.Rating
import com.typeform.schema.ShortText
import com.typeform.schema.Statement
import com.typeform.schema.YesNo

@Deprecated(message = "Use kotlinx-serialization.", replaceWith = ReplaceWith("FieldPropertiesContract"))
data class FlatFieldProperties(
    val description: String? = null,
    // Datestamp
    val separator: String? = null,
    val structure: String? = null,
    // Rating
    val shape: String? = null,
    // Rating, Opinion Scale
    val steps: Int? = null,
    // Opinion Scale
    val labels: OpinionScale.Labels? = null,
    val start_at_one: Boolean? = null,
    // Dropdown, Multiple Choice
    val choices: List<Choice>? = null,
    val randomize: Boolean? = null,
    // Dropdown
    val alphabetical_order: Boolean? = null,
    // Multiple Choice
    val allow_multiple_selection: Boolean? = null,
    val allow_other_choice: Boolean? = null,
    val vertical_alignment: Boolean? = null,
    // Group, Statement
    val button_text: String? = null,
    // Group
    val fields: List<FlatField>? = null,
    val show_button: Boolean? = null,
    // Statement
    val hide_marks: Boolean? = null,
) {
    companion object {
    }
}

fun FieldProperties.Companion.make(flatFieldProperties: FlatFieldProperties, fieldType: FieldType): FieldProperties {
    return when (fieldType) {
        FieldType.DATE -> {
            FieldProperties.DateStampProperties(
                DateStamp(
                    separator = flatFieldProperties.separator ?: "",
                    structure = flatFieldProperties.structure ?: "",
                    description = flatFieldProperties.description,
                ),
            )
        }
        FieldType.DROPDOWN -> {
            FieldProperties.DropdownProperties(
                Dropdown(
                    choices = flatFieldProperties.choices ?: emptyList(),
                    randomize = flatFieldProperties.randomize ?: false,
                    alphabetical_order = flatFieldProperties.alphabetical_order ?: false,
                ),
            )
        }
        FieldType.GROUP -> {
            FieldProperties.GroupProperties(
                Group(
                    button_text = flatFieldProperties.button_text ?: "",
                    fields = flatFieldProperties.fields?.map { Field.make(it) } ?: emptyList(),
                    show_button = flatFieldProperties.show_button ?: false,
                ),
            )
        }
        FieldType.LONG_TEXT -> {
            FieldProperties.LongTextProperties(
                LongText(
                    description = flatFieldProperties.description,
                ),
            )
        }
        FieldType.MULTIPLE_CHOICE -> {
            FieldProperties.MultipleChoiceProperties(
                MultipleChoice(
                    choices = flatFieldProperties.choices ?: emptyList(),
                    randomize = flatFieldProperties.randomize ?: false,
                    allow_multiple_selection = flatFieldProperties.allow_multiple_selection ?: false,
                    allow_other_choice = flatFieldProperties.allow_other_choice ?: false,
                    vertical_alignment = flatFieldProperties.vertical_alignment ?: false,
                    description = flatFieldProperties.description,
                ),
            )
        }
        FieldType.NUMBER -> {
            FieldProperties.NumberProperties(
                com.typeform.schema.Number(
                    description = flatFieldProperties.description,
                ),
            )
        }
        FieldType.OPINION_SCALE -> {
            FieldProperties.OpinionScaleProperties(
                OpinionScale(
                    steps = flatFieldProperties.steps ?: 0,
                    labels = flatFieldProperties.labels ?: OpinionScale.Labels(),
                    start_at_one = flatFieldProperties.start_at_one ?: false
                )
            )
        }
        FieldType.RATING -> {
            FieldProperties.RatingProperties(
                Rating(
                    shape = flatFieldProperties.shape ?: "",
                    steps = flatFieldProperties.steps ?: 0,
                    description = flatFieldProperties.description,
                ),
            )
        }
        FieldType.SHORT_TEXT -> {
            FieldProperties.ShortTextProperties(
                ShortText(
                    description = flatFieldProperties.description,
                ),
            )
        }
        FieldType.STATEMENT -> {
            FieldProperties.StatementProperties(
                Statement(
                    hide_marks = flatFieldProperties.hide_marks ?: false,
                    button_text = flatFieldProperties.button_text ?: "",
                    description = flatFieldProperties.description,
                ),
            )
        }
        FieldType.YES_NO -> {
            FieldProperties.YesNoProperties(
                YesNo(
                    description = flatFieldProperties.description,
                ),
            )
        }
    }
}

fun FlatFieldProperties.Companion.make(fieldProperties: FieldProperties): FlatFieldProperties {
    return when (fieldProperties) {
        is FieldProperties.DateStampProperties -> {
            FlatFieldProperties(
                separator = fieldProperties.properties.separator,
                structure = fieldProperties.properties.structure,
                description = fieldProperties.properties.description,
            )
        }
        is FieldProperties.DropdownProperties -> {
            FlatFieldProperties(
                choices = fieldProperties.properties.choices,
                randomize = fieldProperties.properties.randomize,
                alphabetical_order = fieldProperties.properties.alphabetical_order,
            )
        }
        is FieldProperties.GroupProperties -> {
            FlatFieldProperties(
                button_text = fieldProperties.properties.button_text,
                fields = fieldProperties.properties.fields.map { FlatField.make(it) },
                show_button = fieldProperties.properties.show_button,
            )
        }
        is FieldProperties.LongTextProperties -> {
            FlatFieldProperties(
                description = fieldProperties.properties.description,
            )
        }
        is FieldProperties.MultipleChoiceProperties -> {
            FlatFieldProperties(
                choices = fieldProperties.properties.choices,
                randomize = fieldProperties.properties.randomize,
                allow_multiple_selection = fieldProperties.properties.allow_multiple_selection,
                allow_other_choice = fieldProperties.properties.allow_other_choice,
                vertical_alignment = fieldProperties.properties.vertical_alignment,
                description = fieldProperties.properties.description,
            )
        }
        is FieldProperties.NumberProperties -> {
            FlatFieldProperties(
                description = fieldProperties.properties.description,
            )
        }
        is FieldProperties.OpinionScaleProperties -> {
            FlatFieldProperties(
                steps = fieldProperties.properties.steps,
                labels = fieldProperties.properties.labels,
                start_at_one = fieldProperties.properties.start_at_one,
            )
        }
        is FieldProperties.RatingProperties -> {
            FlatFieldProperties(
                shape = fieldProperties.properties.shape,
                steps = fieldProperties.properties.steps,
                description = fieldProperties.properties.description,
            )
        }
        is FieldProperties.ShortTextProperties -> {
            FlatFieldProperties(
                description = fieldProperties.properties.description,
            )
        }
        is FieldProperties.StatementProperties -> {
            FlatFieldProperties(
                hide_marks = fieldProperties.properties.hide_marks,
                button_text = fieldProperties.properties.button_text,
                description = fieldProperties.properties.description,
            )
        }
        is FieldProperties.YesNoProperties -> {
            FlatFieldProperties(
                description = fieldProperties.properties.description,
            )
        }
    }
}
