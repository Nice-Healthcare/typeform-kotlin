package com.typeform.schema.logic

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = Action.Serializer::class)
data class Action(
    val action: ActionType,
    val details: ActionDetails,
    val condition: Condition,
) {
    companion object {
        private val serializer = Contract.serializer()
    }

    @Serializable
    internal data class Contract(
        val action: ActionType,
        val details: ActionDetails,
        val condition: Condition.Contract,
    ) {
        constructor(action: Action) : this(
            action = action.action,
            details = action.details,
            condition = Condition.Contract(action.condition),
        )

        fun toAction(): Action {
            return Action(
                action = action,
                details = details,
                condition = condition.toCondition() ?: Condition(),
            )
        }
    }

    private object Serializer : KSerializer<Action> {
        override val descriptor: SerialDescriptor
            get() = serializer.descriptor

        override fun serialize(
            encoder: Encoder,
            value: Action,
        ) {
            serializer.serialize(encoder, Contract(value))
        }

        override fun deserialize(decoder: Decoder): Action {
            return serializer.deserialize(decoder).toAction()
        }
    }
}
