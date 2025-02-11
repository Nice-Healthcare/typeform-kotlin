package com.typeform.adapters

import com.typeform.schema.Field
import com.typeform.schema.FieldProperties
import com.typeform.schema.FieldType
import com.typeform.schema.Validations

@Deprecated(message = "Use kotlinx-serialization.", replaceWith = ReplaceWith("FieldContract"))
data class FlatField(
    val id: String,
    val ref: String,
    val type: FieldType,
    val title: String,
    val properties: FlatFieldProperties,
    val validations: Validations?,
) {
    companion object {
    }
}

fun Field.Companion.make(flatField: FlatField): Field {
    return Field(
        id = flatField.id,
        ref = flatField.ref,
        type = flatField.type,
        title = flatField.title,
        properties = FieldProperties.make(flatField.properties, flatField.type),
        validations = flatField.validations,
        attachment = null,
    )
}

fun FlatField.Companion.make(field: Field): FlatField {
    return FlatField(
        id = field.id,
        ref = field.ref,
        type = field.type,
        title = field.title,
        properties = FlatFieldProperties.make(field.properties),
        validations = field.validations,
    )
}
