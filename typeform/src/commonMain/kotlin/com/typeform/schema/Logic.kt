package com.typeform.schema

import com.typeform.serializers.LogicSerializer
import kotlinx.serialization.Serializable

@Serializable(with = LogicSerializer::class)
data class Logic(
    val ref: String,
    val type: LogicType,
    val actions: List<Action>,
) {
    companion object {
    }
}
