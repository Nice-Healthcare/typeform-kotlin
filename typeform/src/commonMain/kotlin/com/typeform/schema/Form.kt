package com.typeform.schema

import com.typeform.models.Position
import com.typeform.models.Responses
import com.typeform.models.TypeformException
import com.typeform.models.responseRequiredFor

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
     * Determine the first [Position] that should be displayed given the provided [Responses].
     *
     * When preloading responses for the form, the _first_ field may no longer be the one desired to be shown.
     *
     * @param skipWelcomeScreen Flag indicated whether a 'Welcome Screen' should be skipped if present.
     * @param responses Any responses that are already known when the form is first presented.
     * @return The first [Position] to be presented.
     * @throws [TypeformException]
     */
    @Throws(TypeformException::class)
    fun firstPosition(skipWelcomeScreen: Boolean, responses: Responses): Position {
        if (!skipWelcomeScreen) {
            firstScreen?.let { screen ->
                return Position.ScreenPosition(screen)
            }
        }

        val field = fields.firstOrNull()
        if (field == null) {
            defaultOrFirstThankYouScreen?.let {
                return Position.ScreenPosition(it)
            }

            throw TypeformException.FirstPosition
        }

        return nextPosition(
            from = Position.FieldPosition(field, null),
            responses = responses
        )
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
                var next: Position = Position.FieldPosition(from.field, from.group)
                try {
                    next = nextPositionFrom(from.field, from.group, responses)
                    var requiresResponse = false

                    while (!requiresResponse) {
                        when (next) {
                            is Position.ScreenPosition -> {
                                requiresResponse = true
                            }
                            is Position.FieldPosition -> {
                                if (responses[next.field.ref] == null) {
                                    requiresResponse = true
                                } else {
                                    next = nextPositionFrom(next.field, next.group, responses)
                                }
                            }
                        }
                    }

                    return next
                } catch (exception: TypeformException.ResponseValueRequired) {
                    return next
                }
            }
        }
    }
}

@Throws(TypeformException::class)
private fun Form.nextPositionFrom(field: Field, group: Group?, responses: Responses): Position {
    val currentPosition = Position.FieldPosition(field, group)

    // Is a response required of the current position?
    if (responses.responseRequiredFor(field)) {
        throw TypeformException.ResponseValueRequired
    }

    // Is this [Field] of type 'group'?
    when (field.properties) {
        is FieldProperties.GroupProperties -> {
            val firstGroupField = field.properties.properties.fields.firstOrNull() ?: throw TypeformException.NextPosition(currentPosition)
            return Position.FieldPosition(firstGroupField, field.properties.properties)
        }
        else -> {
        }
    }

    // Is there [Logic] that applies to this [Field].
    logic.firstOrNull { it.ref == field.ref }?.let { logic ->
        logic.actions.firstOrNull { it.condition.satisfiedGiven(responses) == true }?.let { action ->
            when (action.details.to.type) {
                ActionDetails.ToType.FIELD -> {
                    return parentForFieldWithRef(action.details.to.value) ?: throw TypeformException.NextPosition(currentPosition)
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

                    throw TypeformException.NextPosition(currentPosition)
                }
            }
        }
    }

    // Are we already in a 'group'?
    group?.let { theGroup ->
        // Is there a next [Field]?
        val fieldIndex = theGroup.fields.indexOfFirst { it.id == field.id }
        if (fieldIndex == -1) {
            throw TypeformException.NextPosition(currentPosition)
        }

        theGroup.fields.getOrNull(fieldIndex + 1)?.let { nextField ->
            return Position.FieldPosition(nextField, theGroup)
        }

        // Is the 'group' complete?
        ancestorForFieldWithRef(field.ref)?.let { ancestor ->
            when (ancestor) {
                is Position.FieldPosition -> {
                    val ancestorIndex = fields.indexOfFirst { it.id == ancestor.field.id }
                    if (ancestorIndex == -1) {
                        throw TypeformException.NextPosition(currentPosition)
                    }

                    fields.getOrNull(ancestorIndex + 1)?.let { nextField ->
                        return Position.FieldPosition(nextField, null)
                    }
                }
                else -> {
                    throw TypeformException.NextPosition(currentPosition)
                }
            }
        }
    }

    // If the field has no logic and is not in a group, what is the next field...
    val index = fields.indexOfFirst { it.id == field.id }
    if (index != -1) {
        fields.getOrNull(index + 1)?.let { nextField ->
            return Position.FieldPosition(nextField, null)
        }
    }

    throw TypeformException.NextPosition(currentPosition)
}
