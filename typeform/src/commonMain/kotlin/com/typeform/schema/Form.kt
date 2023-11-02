package com.typeform.schema

import com.typeform.models.Position
import com.typeform.models.Responses
import com.typeform.models.TypeformException

data class Form(
    val id: String,
    val type: FormType,
    val logic: List<Logic>,
    val theme: Theme,
    val title: String,
    val _links: Links,
    val fields: List<Field>,
    val hidden: List<String>?,
    val settings: Settings,
    val workspace: Workspace,
    val welcome_screens: List<WelcomeScreen>?,
    val thankyou_screens: List<ThankYouScreen>,
) {
    companion object {
    }

    /**
     * The first [Screen] that is presented for a specific [Form].
     *
     * In a properly formatted [Form] this will be a [WelcomeScreen].
     */
    val firstScreen: Screen?
        get() {
            return welcome_screens?.firstOrNull()
        }

    /**
     * The [ThankYouScreen] identified as the **default**, or the first available if none.
     */
    val defaultOrFirstThankYouScreen: ThankYouScreen?
        get() {
            return thankyou_screens.firstOrNull { it.isDefault } ?: thankyou_screens.firstOrNull()
        }

    fun screenWithId(id: String): Screen? {
        var screen: Screen?
        screen = welcome_screens?.firstOrNull { it.id == id }
        if (screen != null) {
            return screen
        }

        screen = thankyou_screens.firstOrNull { it.id == id }
        if (screen != null) {
            return screen
        }

        return screen
    }

    /**
     * Locate a [Field] by id.
     *
     * This will also examine _sub-groups_ for a matching id.
     *
     * @param id Unique identifier of the [Field] being requested.
     * @return A [Field] anywhere in the hierarchy that matches the parameters.
     */
    fun fieldWithId(id: String): Field? = fields.fieldWithId(id)

    /**
     * Locate a [Field] by _reference_.
     *
     * This will also examine _sub-groups_ for a matching reference.
     *
     * @param ref Reference identifier of the [Field] being requested.
     * @return A [Field] anywhere in the hierarchy that matches the parameters.
     */
    fun fieldWithRef(ref: String): Field? = fields.fieldWithRef(ref)

    /**
     * Identify the [Position] of the immediate parent for the specified [Field] id.
     *
     * @param id Unique identifier of the [Field] being requested.
     * @return The [Position] associated to the parent if located.
     */
    fun parentForFieldWithId(id: String): Position? = fields.parentForFieldWithId(id)

    /**
     * Identify the [Position] of the immediate parent for the specified [Field] reference.
     *
     * @param ref _Reference_ identifier of the [Field] being requested.
     * @return The [Position] associated to the parent if located.
     */
    fun parentForFieldWithRef(ref: String): Position? = fields.parentForFieldWithRef(ref)

    /**
     * Locate the most distant ancestor (i.e. root) for the [Field] id provided.
     *
     * @param id Unique identifier of the [Field] being requested.
     * @return The [Position] associated to the ancestor if located.
     */
    fun ancestorForFieldWithId(id: String): Position? {
        for (field in fields) {
            if (field.id == id) {
                return Position.FieldPosition(field, null)
            }

            val subGroup = field.properties.asGroup()
            if (subGroup != null) {
                if (subGroup.fields.fieldWithId(id) != null) {
                    return Position.FieldPosition(field, subGroup)
                }
            }
        }

        return null
    }

    /**
     * Locate the most distant ancestor (i.e. root) for the [Field] reference provided.
     *
     * @param ref _Reference_ identifier of the [Field] being requested.
     * @return The [Position] associated to the ancestor if located.
     */
    fun ancestorForFieldWithRef(ref: String): Position? {
        for (field in fields) {
            if (field.ref == ref) {
                return Position.FieldPosition(field, null)
            }

            val subGroup = field.properties.asGroup()
            if (subGroup != null) {
                if (subGroup.fields.fieldWithRef(ref) != null) {
                    return Position.FieldPosition(field, subGroup)
                }
            }
        }

        return null
    }

    /**
     * Determine the next [Field] or [Screen] given the current [Position] and [Responses].
     *
     * @param from The current [Position] in the flow of the [Form].
     * @param responses Field references and values collected while interacting with the form.
     * @return The next [Position] which should be presented in the flow. (If `null`, the position could not be determined)
     * @throws [TypeformException]
     */
    @Throws(TypeformException::class)
    fun nextPosition(from: Position, responses: Responses): Position {
        when (from) {
            is Position.ScreenPosition -> {
                when (from.screen) {
                    is WelcomeScreen -> {
                        val field = fields.firstOrNull()
                        if (field != null) {
                            return Position.FieldPosition(field, null)
                        }

                        val screen = defaultOrFirstThankYouScreen
                        if (screen != null) {
                            return Position.ScreenPosition(screen)
                        }

                        throw TypeformException.NextPosition(from)
                    }
                    else -> {
                        throw TypeformException.NextPosition(from)
                    }
                }
            }
            is Position.FieldPosition -> {
                // If the `Field` requires a response, and there is none given...
                val responseGiven = responses[from.field.ref]
                if (from.field.validations?.required == true && responseGiven == null) {
                    throw TypeformException.ResponseValueRequired
                }

                // Is the `FieldType` GROUP? Position at the first question in the group...
                when (from.field.properties) {
                    is FieldProperties.GroupProperties -> {
                        val field = from.field.properties.properties.fields.firstOrNull() ?: throw TypeformException.NextPosition(from)
                        return Position.FieldPosition(field, from.field.properties.properties)
                    }
                    else -> {
                    }
                }

                // Is there specific `Logic` for the `Field`? Process it for `Action`s...
                logic.firstOrNull { it.ref == from.field.ref }?.let { logic ->
                    logic.actions.firstOrNull { it.condition.satisfiedGiven(responses) == true }?.let { action ->
                        when (action.details.to.type) {
                            ActionDetails.ToType.FIELD -> {
                                return parentForFieldWithRef(action.details.to.value) ?: throw TypeformException.NextPosition(from)
                            }
                            ActionDetails.ToType.THANK_YOU -> {
                                var screen = thankyou_screens.firstOrNull { it.ref == action.details.to.value }
                                if (screen != null) {
                                    return Position.ScreenPosition(screen)
                                }

                                screen = thankyou_screens.firstOrNull { it.isDefault }
                                if (screen != null) {
                                    return Position.ScreenPosition(screen)
                                }

                                throw TypeformException.NextPosition(from)
                            }
                        }
                    }
                }

                // Are we already in a group? What's the next field...
                from.group?.let { group ->
                    val index = group.fields.indexOfFirst { it.id == from.field.id }
                    if (index == -1) {
                        throw TypeformException.NextPosition(from)
                    }

                    group.fields.getOrNull(index + 1)?.let { nextField ->
                        return Position.FieldPosition(nextField, group)
                    }
                }

                // If the group is complete, what's the next field...
                ancestorForFieldWithRef(from.field.ref)?.let { ancestor ->
                    when (ancestor) {
                        is Position.FieldPosition -> {
                            val index = fields.indexOfFirst { it.id == ancestor.field.id }
                            if (index == -1) {
                                throw TypeformException.NextPosition(from)
                            }

                            fields.getOrNull(index + 1)?.let { nextField ->
                                return Position.FieldPosition(nextField, null)
                            }
                        }
                        else -> {
                            throw TypeformException.NextPosition(from)
                        }
                    }
                }

                // If the field has no logic and is not in a group, what is the next field...
                val index = fields.indexOfFirst { it.id == from.field.id }
                if (index != -1) {
                    fields.getOrNull(index + 1)?.let { nextField ->
                        return Position.FieldPosition(nextField, null)
                    }
                }

                throw TypeformException.NextPosition(from)
            }
        }
    }
}
