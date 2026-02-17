package com.typeform.schema.structure

data class Group(
    val button_text: String,
    override val fields: List<Field>,
    val show_button: Boolean,
) : FieldContainer {
    companion object {
    }
}
