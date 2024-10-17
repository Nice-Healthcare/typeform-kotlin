package com.typeform.schema

import com.typeform.models.Position
import com.typeform.serializers.FieldSerializer
import kotlinx.serialization.Serializable

@Serializable(with = FieldSerializer::class)
data class Field(
    val id: String,
    val ref: String,
    val type: FieldType,
    val title: String,
    val properties: FieldProperties,
    val validations: Validations?,
) {
    companion object {
    }
}

/**
 * Locate a [Field] in the collection with the specified id.
 *
 * This will also examine _sub-groups_ for a matching id.
 *
 * @param id Unique identifier of the [Field] being requested.
 * @return A [Field] anywhere in the hierarchy that matches the parameters.
 */
fun List<Field>.fieldWithId(id: String): Field? {
    for (field in this) {
        if (field.id == id) {
            return field
        }

        val subGroup = field.properties.asGroup()
        if (subGroup != null) {
            val match = subGroup.fields.fieldWithId(id)
            if (match != null) {
                return match
            }
        }
    }

    return null
}

/**
 * Locate a [Field] in the collection with the specified _reference_.
 *
 * This will also examine _sub-groups_ for a matching reference.
 *
 * @param ref Reference identifier of the [Field] being requested.
 * @return A [Field] anywhere in the hierarchy that matches the parameters.
 */
fun List<Field>.fieldWithRef(ref: String): Field? {
    for (field in this) {
        if (field.ref == ref) {
            return field
        }

        val subGroup = field.properties.asGroup()
        if (subGroup != null) {
            val match = subGroup.fields.fieldWithRef(ref)
            if (match != null) {
                return match
            }
        }
    }

    return null
}

/**
 * Locate a [Field] which is the immediate parent of the a [Field] with the specified identifier.
 *
 * **This always assumes that you are starting at the top of a `Form` hierarchy, and should only be used there.**
 *
 * @param id Unique identifier of the [Field] being requested.
 * @param group The optional [Group] of which the field provided belongs.
 * @return The [Position] associated to the parent if located.
 */
internal fun List<Field>.parentForFieldWithId(
    id: String,
    group: Group? = null,
): Position? {
    for (field in this) {
        if (field.id == id) {
            return Position.FieldPosition(field, group)
        }

        val subGroup = field.properties.asGroup()
        if (subGroup != null) {
            val match = subGroup.fields.parentForFieldWithId(id, subGroup)
            if (match != null) {
                return match
            }
        }
    }

    return null
}

/**
 * Locate a [Field] which is the immediate parent of the a [Field] with the specified _reference_.
 *
 * **This always assumes that you are starting at the top of a `Form` hierarchy, and should only be used there.**
 *
 * @param ref _Reference_ identifier of the [Field] being requested.
 * @param group The optional [Group] of which the field provided belongs.
 * @return The [Position] associated to the parent if located.
 */
internal fun List<Field>.parentForFieldWithRef(
    ref: String,
    group: Group? = null,
): Position? {
    for (field in this) {
        if (field.ref == ref) {
            return Position.FieldPosition(field, group)
        }

        val subGroup = field.properties.asGroup()
        if (subGroup != null) {
            val match = subGroup.fields.parentForFieldWithRef(ref, subGroup)
            if (match != null) {
                return match
            }
        }
    }

    return null
}
