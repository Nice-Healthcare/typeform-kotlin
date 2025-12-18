package com.typeform.schema.structure

import com.typeform.serializers.URLSerializer
import java.net.URL
import kotlinx.serialization.Serializable

@Serializable
data class Links(
    @Serializable(with = URLSerializer::class)
    val display: URL,
)
