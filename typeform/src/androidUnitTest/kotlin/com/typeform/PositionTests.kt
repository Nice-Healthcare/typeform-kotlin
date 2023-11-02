package com.typeform

import com.typeform.models.Position
import com.typeform.models.ResponseValue
import com.typeform.models.Responses
import com.typeform.schema.Choice
import com.typeform.schema.FieldProperties
import com.typeform.schema.FieldType
import com.typeform.schema.fieldWithId
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.fail

class PositionTests : TypeformTestCase() {

    private var responses: Responses = mutableMapOf()

    /**
     * Verify the transition from a [WelcomeScreen] to the first [Field] in a [Form].
     */
    @Test
    fun testNextFromWelcome() {
        val welcomeScreen = assertUnwrap(form.welcome_screens!!.firstOrNull())
        val position: Position = Position.ScreenPosition(welcomeScreen)
        val next: Position
        try {
            next = form.nextPosition(position, responses)
        } catch (_: Exception) {
            fail("Unexpected Exception")
        }

        when (next) {
            is Position.FieldPosition -> {
                assertNull(next.group)
                assertEquals("5kmu6eCtBcYH", next.field.id)
                assertEquals("508ea9df-177c-4cda-8371-8f7cc1bc60a2", next.field.ref)
                assertEquals("What state do you live in?", next.field.title)
                assertEquals(FieldType.DROPDOWN, next.field.type)
            }
            else -> {
                fail("Unexpected Position")
            }
        }
    }

    /**
     * Verify that [Responses] change the result of calling `Form.nextPosition()`
     */
    @Test
    fun testLogicChangesNext() {
        val stateField = assertUnwrap(form.fieldWithId("5kmu6eCtBcYH"))
        val states = when (stateField.properties) {
            is FieldProperties.DropdownProperties -> {
                val properties = (stateField.properties as FieldProperties.DropdownProperties).properties
                val minnesota = assertUnwrap(properties.choices.firstOrNull { it.id == "ki7l02wXkJJB" })
                val louisiana = assertUnwrap(properties.choices.firstOrNull { it.id == "44eDpNiPK9Lp" })
                Pair(minnesota, louisiana)
            }
            else -> {
                null
            }
        }

        if (states == null) {
            fail("Invalid States")
        }

        responses = mutableMapOf(
            Pair(stateField.ref, ResponseValue.ChoiceValue(states.second)),
        )

        val position: Position = Position.FieldPosition(stateField, null)

        var next: Position
        try {
            next = form.nextPosition(position, responses)
        } catch (_: Exception) {
            fail("Unexpected Exception")
        }

        when (next) {
            is Position.FieldPosition -> {
                assertNull(next.group)
                assertEquals("eG0YCnJUpu1N", next.field.id)
            }
            else -> {
                fail("Unexpected Position")
            }
        }

        responses = mutableMapOf(
            Pair(stateField.ref, ResponseValue.ChoiceValue(states.first)),
        )

        try {
            next = form.nextPosition(position, responses)
        } catch (_: Exception) {
            fail("Unexpected Exception")
        }

        when (next) {
            is Position.FieldPosition -> {
                assertNull(next.group)
                assertEquals("qCewzRklnpSW", next.field.id)
            }
            else -> {
                fail("Unexpected Position")
            }
        }
    }

    /**
     * Verify a [Group] of questions can be entered from a [Field] without specific [Logic].
     */
    @Test
    fun testBeginGroup() {
        responses = mutableMapOf(
            Pair(visitState, ResponseValue.ChoiceValue(minnesota)),
            Pair(patientAge, ResponseValue.ChoiceValue(adult)),
            Pair(patientBiologicalSex, ResponseValue.ChoiceValue(male)),
        )

        val genderField = assertUnwrap(form.fieldWithId("gFDX3w26M8yg"))
        var position: Position = Position.FieldPosition(genderField, null)

        var next: Position
        try {
            next = form.nextPosition(position, responses)
        } catch (_: Exception) {
            fail("Unexpected Exception")
        }

        when (next) {
            is Position.FieldPosition -> {
                assertNull(next.group)
                assertEquals("us8oIodSBXkx", next.field.id)
                assertEquals(FieldType.GROUP, next.field.type)

                position = Position.FieldPosition(next.field, next.group)
            }
            else -> {
                fail("Unexpected Position")
            }
        }

        try {
            next = form.nextPosition(position, responses)
        } catch (_: Exception) {
            fail("Unexpected Exception")
        }

        when (next) {
            is Position.FieldPosition -> {
                assertEquals("33TQPEdKii8T", next.field.id)
                assertEquals(4, next.group?.fields?.count())
            }
            else -> {
                fail("Unexpected Position")
            }
        }
    }

    /**
     * Verify leaving a [Group] without specific [Logic] returns to the next [Field].
     */
    @Test
    fun testEndGroup() {
        responses = mutableMapOf(
            Pair(visitState, ResponseValue.ChoiceValue(minnesota)),
            Pair(patientAge, ResponseValue.ChoiceValue(adult)),
            Pair(patientBiologicalSex, ResponseValue.ChoiceValue(male)),
            Pair("7f57a989-19d3-40b8-af66-d022d3ebb73f", ResponseValue.BooleanValue(true)),
            Pair("5d99768b-65af-4f68-9939-87dfbd29f49a", ResponseValue.BooleanValue(false)),
            Pair(
                visitReason,
                ResponseValue.ChoiceValue(
                    Choice(
                        id = "BQbGWsw0MzIZ",
                        ref = "c9291e36-0cf3-454c-b9f6-c96538ef6b5f",
                        label = "A follow-up visit for continued wellness coaching",
                    ),
                ),
            ),
        )

        val location = assertUnwrap(form.fieldWithId("XisVeizxVZm0"))
        val position = when (location.properties) {
            is FieldProperties.GroupProperties -> {
                val properties = (location.properties as FieldProperties.GroupProperties).properties
                val parkingInstructions = assertUnwrap(properties.fields.fieldWithId("giRxNIAxLYEh"))
                Position.FieldPosition(parkingInstructions, properties)
            }
            else -> {
                null
            }
        }

        if (position == null) {
            fail("Invalid Position")
        }

        val next: Position
        try {
            next = form.nextPosition(position, responses)
        } catch (_: Exception) {
            fail("Unexpected Exception")
        }

        when (next) {
            is Position.FieldPosition -> {
                assertEquals("G8f85EPqK4wy", next.field.id)
            }
            else -> {
                fail("Unexpected Position")
            }
        }
    }

    /**
     * Verify a field without [Logic] proceeds to the next [Field].
     */
    @Test
    fun testNoLogicNextField() {
        responses = mutableMapOf(
            Pair(visitState, ResponseValue.ChoiceValue(minnesota)),
            Pair(patientAge, ResponseValue.ChoiceValue(adult)),
            Pair(patientBiologicalSex, ResponseValue.ChoiceValue(male)),
            Pair("7f57a989-19d3-40b8-af66-d022d3ebb73f", ResponseValue.BooleanValue(true)),
            Pair("5d99768b-65af-4f68-9939-87dfbd29f49a", ResponseValue.BooleanValue(false)),
            Pair(
                visitReason,
                ResponseValue.ChoiceValue(
                    Choice(
                        id = "BQbGWsw0MzIZ",
                        ref = "c9291e36-0cf3-454c-b9f6-c96538ef6b5f",
                        label = "A follow-up visit for continued wellness coaching",
                    ),
                ),
            ),
        )

        val whatsNext = assertUnwrap(form.fieldWithId("G8f85EPqK4wy"))
        val position = Position.FieldPosition(whatsNext, null)

        val next: Position
        try {
            next = form.nextPosition(position, responses)
        } catch (_: Exception) {
            fail("Unexpected Exception")
        }

        when (next) {
            is Position.FieldPosition -> {
                assertEquals("VIFWzjRMJ9sE", next.field.id)
            }
            else -> {
                fail("Unexpected Position")
            }
        }
    }

    @Test
    fun testAcuteSymptomsFlow() {
        responses = mutableMapOf(
            Pair(visitState, ResponseValue.ChoiceValue(minnesota)),
            Pair(patientAge, ResponseValue.ChoiceValue(adult)),
            Pair(patientBiologicalSex, ResponseValue.ChoiceValue(male)),
            Pair(visitReason, ResponseValue.ChoiceValue(acute)),
        )

        val acuteSymptoms = assertUnwrap(form.fieldWithId("JuUoSnHTjrsG"))
        val acuteSymptomsGroup = assertUnwrap(acuteSymptoms.properties.asGroup())
        val whatBringsYouIn = assertUnwrap(acuteSymptomsGroup.fields.fieldWithId("t3tSlQwaZjbR"))
        val position = Position.FieldPosition(whatBringsYouIn, acuteSymptomsGroup)

        val next: Position
        try {
            next = form.nextPosition(position, responses)
        } catch (_: Exception) {
            fail("Unexpected Exception")
        }

        when (next) {
            is Position.FieldPosition -> {
                assertEquals("ahAge9YYr2DE", next.field.id)
                assertEquals(17, next.group?.fields?.count())
            }
            else -> {
                fail("Unexpected Position")
            }
        }
    }
}
