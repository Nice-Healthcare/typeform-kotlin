package com.typeform.schema.structure

import com.typeform.schema.questions.MultipleChoice

data class Matrix(
    override val fields: List<Field>,
) : FieldContainer {

    data class Iteration(
        val id: String,
        val ref: String,
        val title: String,
        val question: MultipleChoice,
    )

    companion object {
    }

    val questions: List<Iteration>
        get() {
            return fields.mapNotNull { field ->
                val multipleChoice = field.properties.asMultipleChoice() ?: return@mapNotNull null
                return@mapNotNull Iteration(
                    id = field.id,
                    ref = field.ref,
                    title = field.title,
                    question = multipleChoice,
                )
            }
        }

    val columns: List<String>
        get() {
            val multipleChoice = fields.firstOrNull()?.properties?.asMultipleChoice() ?: return emptyList()
            return multipleChoice.choices.map { it.label }
        }

    val rows: List<String>
        get() {
            return questions.map { it.title }
        }

    val allow_multiple_selections: Boolean
        get() = when (val properties = fields.firstOrNull()?.properties) {
            is FieldProperties.MultipleChoiceProperties -> {
                properties.properties.allow_multiple_selection
            }
            else -> {
                false
            }
        }

    val validations: Validations?
        get() = fields.firstOrNull()?.validations
}
