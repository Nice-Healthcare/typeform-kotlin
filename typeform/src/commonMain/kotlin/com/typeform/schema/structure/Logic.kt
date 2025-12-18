package com.typeform.schema.structure

import com.typeform.schema.logic.Action
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = Logic.Serializer::class)
data class Logic(
    val ref: String,
    val type: LogicType,
    val actions: List<Action>,
) {
    companion object {
        private val serializer = Contract.serializer()
    }

    @Serializable
    internal data class Contract(
        val ref: String,
        val type: LogicType,
        val actions: List<Action.Contract>,
    ) {
        constructor(logic: Logic) : this(
            ref = logic.ref,
            type = logic.type,
            actions = logic.actions.map { Action.Contract(it) },
        )

        fun toLogic(): Logic {
            return Logic(
                ref = ref,
                type = type,
                actions = actions.map { it.toAction() },
            )
        }
    }

    private object Serializer : KSerializer<Logic> {
        override val descriptor: SerialDescriptor
            get() = serializer.descriptor

        override fun serialize(
            encoder: Encoder,
            value: Logic,
        ) {
            serializer.serialize(encoder, Contract(value))
        }

        override fun deserialize(decoder: Decoder): Logic {
            return serializer.deserialize(decoder).toLogic()
        }
    }
}
