package com.typeform.schema.translation

import com.typeform.schema.structure.Field
import com.typeform.schema.structure.FieldProperties
import kotlinx.serialization.Serializable

@Serializable
data class TranslatedField(
    val id: String,
    val title: String?,
    val properties: TranslatedProperties?,
)

fun Field.merging(translatedField: TranslatedField): Field {
    return copy(
        title = translatedField.title ?: title,
        properties = properties.merging(translatedField.properties),
    )
}

fun FieldProperties.merging(translatedProperties: TranslatedProperties?): FieldProperties {
    if (translatedProperties == null) {
        return this
    }

    return when (this) {
        is FieldProperties.DateStampProperties -> {
            this.copy(
                properties = this.properties.copy(
                    description = translatedProperties.description ?: this.properties.description,
                ),
            )
        }
        is FieldProperties.DropdownProperties -> {
            this.copy(
                properties = this.properties.copy(
                    choices = this.properties.choices.merging(translatedProperties.choices),
                    description = translatedProperties.description ?: this.properties.description,
                ),
            )
        }
        is FieldProperties.FileUploadProperties -> {
            this.copy(
                properties = this.properties.copy(
                    description = translatedProperties.description ?: this.properties.description,
                ),
            )
        }
        is FieldProperties.GroupProperties -> {
            this.copy(
                properties = this.properties.copy(
                    button_text = translatedProperties.buttonText ?: this.properties.button_text,
                    fields = this.properties.fields.merging(translatedProperties.fields),
                ),
            )
        }
        is FieldProperties.LongTextProperties -> {
            this.copy(
                properties = this.properties.copy(
                    description = translatedProperties.description ?: this.properties.description,
                ),
            )
        }
        is FieldProperties.MultipleChoiceProperties -> {
            this.copy(
                properties = this.properties.copy(
                    choices = this.properties.choices.merging(translatedProperties.choices),
                    description = translatedProperties.description ?: this.properties.description,
                ),
            )
        }
        is FieldProperties.NumberProperties -> {
            this.copy(
                properties = this.properties.copy(
                    description = translatedProperties.description ?: this.properties.description,
                ),
            )
        }
        is FieldProperties.OpinionScaleProperties -> {
            this.copy(
                properties = this.properties.copy(
                    labels = translatedProperties.labels ?: this.properties.labels,
                ),
            )
        }
        is FieldProperties.RatingProperties -> {
            this.copy(
                properties = this.properties.copy(
                    description = translatedProperties.description ?: this.properties.description,
                ),
            )
        }
        is FieldProperties.ShortTextProperties -> {
            this.copy(
                properties = this.properties.copy(
                    description = translatedProperties.description ?: this.properties.description,
                ),
            )
        }
        is FieldProperties.StatementProperties -> {
            this.copy(
                properties = this.properties.copy(
                    button_text = translatedProperties.buttonText ?: this.properties.button_text,
                    description = translatedProperties.description ?: this.description,
                ),
            )
        }
        is FieldProperties.YesNoProperties -> {
            this.copy(
                properties = this.properties.copy(
                    description = translatedProperties.description ?: this.properties.description,
                ),
            )
        }
    }
}

fun List<Field>.merging(translatedFields: List<TranslatedField>?): List<Field> {
    if (translatedFields == null) {
        return this
    }

    val fields = this.toMutableList()
    for (field in translatedFields) {
        val index = fields.indexOfFirst { it.id == field.id }
        if (index != -1) {
            fields[index] = fields[index].merging(field)
        }
    }

    return fields
}
