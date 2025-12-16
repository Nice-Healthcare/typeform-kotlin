package com.typeform.schema.logic

import com.typeform.models.Responses
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = Condition.Serializer::class)
data class Condition(
    val op: Op = Op.EQUAL,
    val parameters: Parameters = Parameters.Conditions(emptyList()),
) {
    companion object {
        private val serializer = Contract.serializer()
    }

    sealed class Parameters {
        data class Vars(
            val vars: List<Var>,
        ) : Parameters()

        data class Conditions(
            val conditions: List<Condition>,
        ) : Parameters()

        companion object {
        }
    }

    fun satisfiedGiven(responses: Responses): Boolean {
        if (op == Op.ALWAYS) {
            return true
        }

        val satisfied = when (parameters) {
            is Parameters.Vars -> {
                parameters.vars.compactMatchGiven(responses, op)
            }
            is Parameters.Conditions -> {
                parameters.conditions.map { it.satisfiedGiven(responses) }
            }
        }

        if (satisfied.isEmpty()) {
            return false
        }

        return when (op) {
            Op.AND -> {
                !satisfied.contains(false)
            }
            Op.OR -> {
                satisfied.contains(true)
            }
            Op.IS -> {
                !satisfied.contains(false)
            }
            Op.IS_NOT -> {
                !satisfied.contains(true)
            }
            else -> {
                // Equatable - [Var].compactMatchGiven(responses:op:)
                !satisfied.contains(false)
            }
        }
    }

    @Serializable
    internal data class Contract(
        val op: Op?,
        val vars: List<Contract>?,
        val type: VarType?,
        val value: Var.Value?,
    ) {
        constructor(condition: Condition) : this(
            op = condition.op,
            vars = when (condition.parameters) {
                is Parameters.Vars -> {
                    condition.parameters.vars.map { Contract(it) }
                }
                is Parameters.Conditions -> {
                    condition.parameters.conditions.map { Contract(it) }
                }
            },
            type = null,
            value = null,
        )

        constructor(`var`: Var) : this(
            op = null,
            vars = null,
            type = `var`.type,
            value = `var`.value,
        )

        fun toCondition(): Condition? {
            if (op != null && vars != null) {
                val conditions = vars.mapNotNull { it.toCondition() }
                val vars = vars.mapNotNull { it.toVar() }

                return if (conditions.isNotEmpty()) {
                    Condition(
                        op = op,
                        parameters = Parameters.Conditions(conditions),
                    )
                } else {
                    Condition(
                        op = op,
                        parameters = Parameters.Vars(vars),
                    )
                }
            }

            return null
        }

        fun toVar(): Var? {
            if (type != null && value != null) {
                return Var(
                    type = type,
                    value = value,
                )
            }

            return null
        }
    }

    private object Serializer : KSerializer<Condition> {
        override val descriptor: SerialDescriptor
            get() = serializer.descriptor

        override fun serialize(
            encoder: Encoder,
            value: Condition,
        ) {
            serializer.serialize(encoder, Contract(value))
        }

        override fun deserialize(decoder: Decoder): Condition {
            TODO("What's happening here?")
            return serializer.deserialize(decoder).toCondition() ?: Condition()
        }
    }
}
