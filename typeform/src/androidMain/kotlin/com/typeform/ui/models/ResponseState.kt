package com.typeform.ui.models

import com.typeform.models.ResponseValue
import com.typeform.models.Responses
import com.typeform.schema.structure.Field
import com.typeform.schema.structure.FieldProperties

data class ResponseState(
    val response: ResponseValue? = null,
    val invalid: Boolean = true,
) {
    constructor(
        field: Field,
        responses: Responses,
    ) : this(
        response = responses[field.ref],
        invalid = when (field.properties) {
            is FieldProperties.GroupProperties -> {
                false
            }
            is FieldProperties.StatementProperties -> {
                false
            }
            else -> {
                true
            }
        },
    )
}
