package com.typeform.schema

import com.typeform.serializers.URLSerializer
import kotlinx.serialization.Serializable
import java.net.URL

@Serializable
data class Theme(
    @Serializable(with = URLSerializer::class)
    val href: URL,
)
