package com.typeform.ui.models

internal sealed class TypeformRoute {
    companion object {
        const val SCREEN = "typeform_screen/{id}"
        const val FIELD = "typeform_field/{id}"
        const val REJECTED = "typeform_rejected"

        fun makeScreen(id: String): String {
            return SCREEN.replace("{id}", id)
        }

        fun makeField(fieldId: String): String {
            return FIELD.replace("{id}", fieldId)
        }
    }
}
