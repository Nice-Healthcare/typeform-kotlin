package com.typeform.schema

import com.typeform.serializers.URLSerializer
import java.net.URL
import kotlinx.serialization.Serializable

@Serializable
data class Theme(
    @Serializable(with = URLSerializer::class)
    val href: URL,
)
