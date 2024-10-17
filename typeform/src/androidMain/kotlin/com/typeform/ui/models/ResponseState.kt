package com.typeform.ui.models

import com.typeform.models.ResponseValue
import com.typeform.models.Responses
import com.typeform.schema.Field

data class ResponseState(
    val response: ResponseValue? = null,
    val invalid: Boolean = true,
) {
    constructor(
        field: Field,
        responses: Responses,
    ) : this(
        response = responses[field.ref],
        invalid = field.properties.asStatement() == null,
    )
}
