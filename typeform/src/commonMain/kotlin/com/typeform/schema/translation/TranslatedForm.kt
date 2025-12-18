package com.typeform.schema.translation

import com.typeform.schema.structure.Form
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TranslatedForm(
    val fields: List<TranslatedField>,
    @SerialName("welcome_screens")
    var welcomeScreens: List<TranslatedScreen>?,
    @SerialName("thankyou_screens")
    val endingScreens: List<TranslatedScreen>?,
)

fun Form.merging(translatedForm: TranslatedForm): Form {
    return this.copy(
        fields = this.fields.merging(translatedForm.fields),
        welcomeScreens = this.welcomeScreens?.mergingWelcomeTranslations(translatedForm.welcomeScreens),
        endingScreens = this.endingScreens.mergingEndingTranslations(translatedForm.endingScreens),
    )
}
