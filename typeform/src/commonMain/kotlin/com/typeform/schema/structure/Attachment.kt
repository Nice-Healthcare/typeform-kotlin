package com.typeform.schema.structure

import kotlinx.serialization.Serializable

@Serializable
data class Attachment(
    val href: String,
    val type: AttachmentType,
    val properties: AttachmentProperties?,
)
