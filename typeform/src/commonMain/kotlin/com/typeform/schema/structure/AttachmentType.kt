package com.typeform.schema.structure

import com.typeform.serializers.AttachmentTypeSerializer
import kotlinx.serialization.Serializable

@Serializable(with = AttachmentTypeSerializer::class)
enum class AttachmentType(val rawValue: String) {
    IMAGE("image"),
    VIDEO("video"),
}
