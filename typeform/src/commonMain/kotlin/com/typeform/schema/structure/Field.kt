package com.typeform.schema.structure

import com.typeform.models.Position
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = Field.Serializer::class)
data class Field(
    val id: String,
    val ref: String,
    val type: FieldType,
    val title: String,
    val properties: FieldProperties,
    val validations: Validations?,
    val attachment: Attachment?,
) {
    companion object {
        private val serializer = Contract.serializer()
    }

    @Serializable
    internal data class Contract(
        val id: String,
        val ref: String,
        val type: FieldType,
        val title: String,
        val properties: FieldProperties.Contract,
        val validations: Validations?,
        val attachment: Attachment?,
    ) {
        constructor(field: Field) : this(
            id = field.id,
            ref = field.ref,
            type = field.type,
            title = field.title,
            properties = FieldProperties.Contract(field.properties),
            validations = field.validations,
            attachment = field.attachment,
        )

        fun toField(): Field {
            return Field(
                id = id,
                ref = ref,
                type = type,
                title = title,
                properties = properties.toFieldProperties(type),
                validations = validations,
                attachment = attachment,
            )
        }
    }

    private object Serializer : KSerializer<Field> {
        override val descriptor: SerialDescriptor
            get() = serializer.descriptor

        override fun serialize(
            encoder: Encoder,
            value: Field,
        ) {
            serializer.serialize(encoder, Contract(value))
        }

        override fun deserialize(decoder: Decoder): Field {
            return serializer.deserialize(decoder).toField()
        }
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
