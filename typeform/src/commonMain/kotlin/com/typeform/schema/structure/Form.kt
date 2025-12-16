package com.typeform.schema.structure

import com.typeform.models.Position
import com.typeform.models.Responses
import com.typeform.models.TypeformException
import com.typeform.models.responseRequiredFor
import com.typeform.schema.logic.ActionDetails
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = Form.Serializer::class)
data class Form(
    val id: String,
    val type: FormType,
    val logic: List<Logic>,
    val theme: Theme,
    val title: String,
    val links: Links,
    val fields: List<Field>,
    val hidden: List<String>?,
    val settings: Settings,
    val workspace: Workspace,
    val welcomeScreens: List<WelcomeScreen>?,
    val endingScreens: List<EndingScreen>,
) {
    companion object {
        private val serializer = Contract.serializer()
    }

    /**
     * The first [Screen] that is presented for a specific [Form].
     *
     * In a properly formatted [Form] this will be a [WelcomeScreen].
     */
    val firstScreen: Screen?
        get() {
            return welcomeScreens?.firstOrNull()
        }

    /**
     * The [EndingScreen] identified as the **default**, or the first available if none.
     */
    val defaultOrFirstEndingScreen: EndingScreen?
        get() {
            return endingScreens.firstOrNull { it.isDefault } ?: endingScreens.firstOrNull()
        }

    fun screenWithId(id: String): Screen? {
        return welcomeScreens?.firstOrNull { it.id == id } ?: endingScreens.firstOrNull { it.id == id }
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
    fun firstPosition(
        skipWelcomeScreen: Boolean,
        responses: Responses,
    ): Position {
        if (!skipWelcomeScreen) {
            firstScreen?.let { screen ->
                return Position.ScreenPosition(screen)
            }
        }

        val field = fields.firstOrNull()
        if (field == null) {
            defaultOrFirstEndingScreen?.let {
                return Position.ScreenPosition(it)
            }

            throw TypeformException.FirstPosition
        }

        if (!responses.containsKey(field.ref)) {
            return Position.FieldPosition(field, null)
        }

        return nextPosition(
            from = Position.FieldPosition(field, null),
            responses = responses,
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
    fun nextPosition(
        from: Position,
        responses: Responses,
    ): Position {
        when (from) {
            is Position.ScreenPosition -> {
                when (from.screen) {
                    is WelcomeScreen -> {
                        val field = fields.firstOrNull()
                        if (field != null) {
                            return Position.FieldPosition(field, null)
                        }

                        val screen = defaultOrFirstEndingScreen
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
                } catch (_: TypeformException.ResponseValueRequired) {
                    return next
                }
            }
        }
    }

    /**
     * A version of [Form] with simplified & flattened structures that has been optimized
     * for out-of-the-box de/serialization using **kotlinx-serialization**.
     */
    @Serializable
    private data class Contract(
        val id: String,
        val type: FormType,
        val logic: List<Logic.Contract>?,
        val theme: Theme,
        val title: String,
        @SerialName("_links")
        val links: Links,
        val fields: List<Field.Contract>,
        val hidden: List<String>?,
        val settings: Settings,
        val workspace: Workspace,
        @SerialName("welcome_screens")
        val welcomeScreens: List<WelcomeScreen>?,
        @SerialName("thankyou_screens")
        val thankYouScreens: List<EndingScreen>,
    ) {
        constructor(form: Form) : this(
            id = form.id,
            type = form.type,
            logic = form.logic.map { Logic.Contract(it) },
            theme = form.theme,
            title = form.title,
            links = form.links,
            fields = form.fields.map { Field.Contract(it) },
            hidden = form.hidden,
            settings = form.settings,
            workspace = form.workspace,
            welcomeScreens = form.welcomeScreens,
            thankYouScreens = form.endingScreens,
        )

        fun toForm(): Form {
            return Form(
                id = id,
                type = type,
                logic = (logic ?: emptyList()).map { it.toLogic() },
                theme = theme,
                title = title,
                links = links,
                fields = fields.map { it.toField() },
                hidden = hidden,
                settings = settings,
                workspace = workspace,
                welcomeScreens = welcomeScreens,
                endingScreens = thankYouScreens,
            )
        }
    }

    private object Serializer : KSerializer<Form> {
        override val descriptor: SerialDescriptor
            get() = serializer.descriptor

        override fun serialize(
            encoder: Encoder,
            value: Form,
        ) {
            serializer.serialize(encoder, Contract(value))
        }

        override fun deserialize(decoder: Decoder): Form {
            return serializer.deserialize(decoder).toForm()
        }
    }
}

@Throws(TypeformException::class)
private fun Form.nextPositionFrom(
    field: Field,
    group: Group?,
    responses: Responses,
): Position {
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
        logic.actions.firstOrNull { it.condition.satisfiedGiven(responses) }?.let { action ->
            when (action.details.to.type) {
                ActionDetails.ToType.FIELD -> {
                    return parentForFieldWithRef(action.details.to.value) ?: throw TypeformException.NextPosition(currentPosition)
                }
                ActionDetails.ToType.THANK_YOU -> {
                    var screen = endingScreens.firstOrNull { it.ref == action.details.to.value }
                    if (screen != null) {
                        return Position.ScreenPosition(screen)
                    }

                    screen = endingScreens.firstOrNull { it.isDefault }
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
        when (val nextIndex = index + 1) {
            fields.count() -> {
                defaultOrFirstEndingScreen?.let {
                    return Position.ScreenPosition(it)
                }
            }
            in 1..fields.count().dec() -> {
                fields.getOrNull(nextIndex)?.let { nextField ->
                    return Position.FieldPosition(nextField, null)
                }
            }
            else -> {
            }
        }
    }

    throw TypeformException.NextPosition(currentPosition)
}
