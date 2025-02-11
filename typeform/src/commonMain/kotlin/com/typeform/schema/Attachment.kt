package com.typeform.schema

import com.typeform.serializers.URLSerializer
import java.net.URL
import kotlinx.serialization.Serializable

@Deprecated(message = "Generalized, use `Attachment`.", replaceWith = ReplaceWith("Attachment"))
typealias ScreenAttachment = Attachment

@Serializable
data class Attachment(
    @Serializable(with = URLSerializer::class)
    val href: URL,
    val type: AttachmentType,
    val properties: AttachmentProperties?,
)
