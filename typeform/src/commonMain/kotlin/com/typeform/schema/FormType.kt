package com.typeform.schema

import com.typeform.serializers.FormTypeSerializer
import kotlinx.serialization.Serializable

@Serializable(with = FormTypeSerializer::class)
enum class FormType(val rawValue: String) {
    QUIZ("quiz"),
}
