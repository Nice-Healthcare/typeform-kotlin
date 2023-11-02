package com.typeform.models

import com.typeform.schema.Field
import com.typeform.schema.FieldProperties
import com.typeform.schema.fieldWithRef

typealias Response = (String, ResponseValue?) -> Unit
typealias Responses = MutableMap<String, ResponseValue>

/**
 * Verify that [ResponseValue]s in the collection are valid given the [Field] definitions.
 *
 * For instance: if a [MultipleChoice] field which only allows a single-selection has more
 * than one [Choice], this is an invalid state.
 *
 * @param fields The fields from a [Form] that are used to validate the collection.
 */
fun Responses.validResponseValuesGiven(fields: List<Field>): Boolean {
    return invalidResponseValuesGiven(fields).isEmpty()
}

/**
 * Collection of invalid _Reference_ keys based on expected [ResponseValue] types.
 *
 * For instance: if a [MultipleChoice] field which only allows a single-selection has more
 * than one [Choice], this is an invalid state.
 *
 * @param fields The fields from a [Form] that are used to validate the collection.
 */
fun Responses.invalidResponseValuesGiven(fields: List<Field>): List<String> {
    return mapNotNull { element ->
        var ref: String? = null

        val field = fields.fieldWithRef(element.key)
        if (field != null) {
            when (field.properties) {
                is FieldProperties.DateStampProperties -> {
                    when (element.value) {
                        is ResponseValue.DateValue -> {
                            // Expected Type
                        }
                        else -> {
                            ref = element.key
                        }
                    }
                }
                is FieldProperties.DropdownProperties -> {
                    when (element.value) {
                        is ResponseValue.ChoiceValue -> {
                            // Expected Type
                        }
                        else -> {
                            ref = element.key
                        }
                    }
                }
                is FieldProperties.GroupProperties -> {
                    ref = element.key
                }
                is FieldProperties.LongTextProperties -> {
                    when (element.value) {
                        is ResponseValue.StringValue -> {
                            // Expected Type
                        }
                        else -> {
                            ref = element.key
                        }
                    }
                }
                is FieldProperties.MultipleChoiceProperties -> {
                    if (field.properties.properties.allow_multiple_selection) {
                        when (element.value) {
                            is ResponseValue.ChoicesValue -> {
                                // Expected Type
                            }
                            else -> {
                                ref = element.key
                            }
                        }
                    } else {
                        when (element.value) {
                            is ResponseValue.ChoiceValue -> {
                                // Expected Type
                            }
                            else -> {
                                ref = element.key
                            }
                        }
                    }
                }
                is FieldProperties.NumberProperties -> {
                    when (element.value) {
                        is ResponseValue.IntValue -> {
                            // Expected Type
                        }
                        else -> {
                            ref = element.key
                        }
                    }
                }
                is FieldProperties.RatingProperties -> {
                    when (element.value) {
                        is ResponseValue.IntValue -> {
                            // Expected Type
                        }
                        else -> {
                            ref = element.key
                        }
                    }
                }
                is FieldProperties.ShortTextProperties -> {
                    when (element.value) {
                        is ResponseValue.StringValue -> {
                            // Expected Type
                        }
                        else -> {
                            ref = element.key
                        }
                    }
                }
                is FieldProperties.StatementProperties -> {
                    ref = element.key
                }
                is FieldProperties.YesNoProperties -> {
                    when (element.value) {
                        is ResponseValue.BooleanValue -> {
                            // Expected Type
                        }
                        else -> {
                            ref = element.key
                        }
                    }
                }
            }
        } else {
            ref = element.key
        }

        ref
    }
}
