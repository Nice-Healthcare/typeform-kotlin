package com.typeform.schema.structure

import kotlinx.serialization.Serializable

@Deprecated(message = "Generalized, use `Attachment`.", replaceWith = ReplaceWith("Attachment"))
typealias ScreenAttachment = Attachment

@Serializable
data class Attachment(
    val href: String,
    val type: AttachmentType,
    val properties: AttachmentProperties?,
)
