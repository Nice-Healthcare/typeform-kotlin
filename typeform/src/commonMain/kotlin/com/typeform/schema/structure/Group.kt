package com.typeform.schema.structure

import com.typeform.schema.Field

data class Group(
    val button_text: String,
    val fields: List<Field>,
    val show_button: Boolean,
) {
    companion object {
    }
}
