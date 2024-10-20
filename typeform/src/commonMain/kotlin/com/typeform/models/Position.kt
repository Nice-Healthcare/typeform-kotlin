package com.typeform.models

import com.typeform.schema.Field
import com.typeform.schema.Group
import com.typeform.schema.Screen

sealed class Position {
    data class ScreenPosition(val screen: Screen) : Position()

    data class FieldPosition(val field: Field, val group: Group?) : Position()

    fun associatedScreen(): Screen? {
        return when (this) {
            is ScreenPosition -> {
                this.screen
            }
            else -> {
                null
            }
        }
    }

    fun associatedField(): Field? {
        return when (this) {
            is FieldPosition -> {
                this.field
            }
            else -> {
                null
            }
        }
    }

    fun associatedGroup(): Group? {
        return when (this) {
            is FieldPosition -> {
                this.group
            }
            else -> {
                null
            }
        }
    }
}
