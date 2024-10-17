package com.typeform.schema

import kotlinx.serialization.Serializable

@Serializable
data class MultipleChoice(
    val choices: List<Choice>,
    val randomize: Boolean,
    val allow_multiple_selection: Boolean,
    val allow_other_choice: Boolean,
    val vertical_alignment: Boolean,
    val description: String?,
) {
    fun orderedChoices(): List<Choice> {
        var choices = this.choices
        if (randomize) {
            choices = choices.shuffled()
        }
        return choices
    }
}
