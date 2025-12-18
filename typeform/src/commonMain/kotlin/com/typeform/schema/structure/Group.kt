package com.typeform.schema.structure

data class Group(
    val button_text: String,
    val fields: List<Field>,
    val show_button: Boolean,
) {
    companion object {
    }
}
