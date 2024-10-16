package com.typeform.contracts

import com.typeform.schema.Field
import com.typeform.schema.FieldType
import com.typeform.schema.Validations
import kotlinx.serialization.Serializable

@Serializable
data class FieldContract(
    val id: String,
    val ref: String,
    val type: FieldType,
    val title: String,
    val properties: FieldPropertiesContract,
    val validations: Validations?,
) {
    constructor(field: Field) : this(
        id = field.id,
        ref = field.ref,
        type = field.type,
        title = field.title,
        properties = FieldPropertiesContract(field.properties),
        validations = field.validations,
    )

    fun toField(): Field {
        return Field(
            id = id,
            ref = ref,
            type = type,
            title = title,
            properties = properties.toFieldProperties(type),
            validations = validations,
        )
    }
}
