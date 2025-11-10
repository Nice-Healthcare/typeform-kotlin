package com.typeform.schema

import com.typeform.serializers.FieldTypeSerializer
import kotlinx.serialization.Serializable

@Serializable(with = FieldTypeSerializer::class)
enum class FieldType(
    val rawValue: String,
) {
    DATE("date"),
    DROPDOWN("dropdown"),
    FILE_UPLOAD("file_upload"),
    GROUP("group"),
    LONG_TEXT("long_text"),
    MULTIPLE_CHOICE("multiple_choice"),
    NUMBER("number"),
    OPINION_SCALE("opinion_scale"),
    RATING("rating"),
    SHORT_TEXT("short_text"),
    STATEMENT("statement"),
    YES_NO("yes_no"),
}
