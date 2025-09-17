package com.typeform.schema.translation

import com.typeform.schema.Choice
import kotlinx.serialization.Serializable

@Serializable
data class TranslatedChoice(
    val id: String,
    val label: String,
)

fun Choice.merging(translatedChoice: TranslatedChoice): Choice {
    return copy(
        label = translatedChoice.label,
    )
}

fun List<Choice>.merging(translatedChoices: List<TranslatedChoice>?): List<Choice> {
    if (translatedChoices == null) {
        return this
    }

    val choices = this.toMutableList()
    for (choice in translatedChoices) {
        val index = choices.indexOfFirst { it.id == choice.id }
        if (index != -1) {
            choices[index] = choices[index].merging(choice)
        }
    }

    return choices
}
