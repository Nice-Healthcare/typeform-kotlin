package com.typeform.schema.translation

import com.typeform.schema.questions.OpinionScale
import com.typeform.schema.structure.ScreenProperties
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TranslatedProperties(
    @SerialName("button_text")
    val buttonText: String?,
    val choices: List<TranslatedChoice>?,
    val description: String?,
    val fields: List<TranslatedField>?,
    val labels: OpinionScale.Labels?,
)

fun ScreenProperties.merging(translatedProperties: TranslatedProperties): ScreenProperties {
    return copy(
        button_text = translatedProperties.buttonText ?: button_text,
    )
}
