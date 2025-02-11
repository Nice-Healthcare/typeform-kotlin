package com.typeform.contracts

import com.typeform.schema.Attachment
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
    val attachment: Attachment?,
) {
    constructor(field: Field) : this(
        id = field.id,
        ref = field.ref,
        type = field.type,
        title = field.title,
        properties = FieldPropertiesContract(field.properties),
        validations = field.validations,
        attachment = field.attachment,
    )

    fun toField(): Field {
        return Field(
            id = id,
            ref = ref,
            type = type,
            title = title,
            properties = properties.toFieldProperties(type),
            validations = validations,
            attachment = attachment,
        )
    }
}
