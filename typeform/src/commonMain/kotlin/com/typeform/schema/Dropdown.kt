package com.typeform.schema

import kotlinx.serialization.Serializable

@Serializable
data class Dropdown(
    val choices: List<Choice>,
    val randomize: Boolean,
    val alphabetical_order: Boolean,
) {
    fun orderedChoices(): List<Choice> {
        var choices = this.choices
        if (alphabetical_order) {
            choices = choices.sortedBy { it.label }
        }
        if (randomize) {
            choices = choices.shuffled()
        }
        return choices
    }
}
