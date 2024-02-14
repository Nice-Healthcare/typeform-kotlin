package com.typeform

import com.typeform.models.Position
import com.typeform.models.ResponseValue
import com.typeform.models.Responses
import com.typeform.schema.Choice
import com.typeform.schema.WelcomeScreen
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class PreloadedResponsesTests: TypeformTestCase() {

    override val jsonResource: String
        get() = "MedicalIntake26.json"

    @Test
    fun testNotSkippingWelcomeEmptyResponses() {
        val responses: Responses = mutableMapOf()
        val position: Position
        try {
            position = form.firstPosition(skipWelcomeScreen = false, responses = responses)
        } catch (_ :Exception) {
            fail("Unexpected Exception")
        }

        when (position) {
            is Position.ScreenPosition -> {
                assertTrue(position.screen is WelcomeScreen)
                assertEquals("NGCT4k03bG7W", position.screen.id)
            }
            is Position.FieldPosition -> {
                fail("Unexpected Position")
            }
        }
    }

    @Test
    fun testNotSkippingWelcomeFirstFieldResponses() {
        val responses: Responses = mutableMapOf(
            Pair(
                "508ea9df-177c-4cda-8371-8f7cc1bc60a2",
                ResponseValue.ChoiceValue(
                    Choice(
                        id = "eSMBTpzeqJYQ",
                        ref = "65fd22c3-32ec-4c92-b194-5aef3a10fe60",
                        label = "Alabama"
                    )
                )
            )
        )

        val position: Position
        try {
            position = form.firstPosition(skipWelcomeScreen = false, responses = responses)
        } catch (_ :Exception) {
            fail("Unexpected Exception")
        }

        when (position) {
            is Position.ScreenPosition -> {
                assertTrue(position.screen is WelcomeScreen)
                assertEquals("NGCT4k03bG7W", position.screen.id)
            }
            is Position.FieldPosition -> {
                fail("Unexpected Position")
            }
        }
    }

    @Test
    fun testSkippingWelcomeEmptyResponses() {
        val responses: Responses = mutableMapOf()

        val position: Position
        try {
            position = form.firstPosition(skipWelcomeScreen = true, responses = responses)
        } catch (_ :Exception) {
            fail("Unexpected Exception")
        }

        when (position) {
            is Position.ScreenPosition -> {
                fail("Unexpected Position")
            }
            is Position.FieldPosition -> {
                assertEquals("89Ofk9JaI4M1", position.field.id)
            }
        }
    }

    @Test
    fun testSkippingWelcomeFirstFieldResponses() {
        val responses: Responses = mutableMapOf(
            Pair(
                "508ea9df-177c-4cda-8371-8f7cc1bc60a2",
                ResponseValue.ChoiceValue(
                    Choice(
                        id = "Rh7fHLNn7tRV",
                        ref = "aa028c7c-ce34-428f-8563-35bce5201dc1",
                        label = "Minnesota"
                    )
                )
            )
        )

        val position: Position
        try {
            position = form.firstPosition(skipWelcomeScreen = true, responses = responses)
        } catch (_ :Exception) {
            fail("Unexpected Exception")
        }

        when (position) {
            is Position.ScreenPosition -> {
                fail("Unexpected Position")
            }
            is Position.FieldPosition -> {
                assertEquals("0mMHJCj4JoPr", position.field.id)
            }
        }
    }

    // There is a 'Statement' field after the first response, and before the second response.
    @Test
    fun testSkippingWelcomePostStatementResponses() {
        val responses: Responses = mutableMapOf(
            Pair(
                "508ea9df-177c-4cda-8371-8f7cc1bc60a2",
                ResponseValue.ChoiceValue(
                    Choice(
                        id = "Rh7fHLNn7tRV",
                        ref = "aa028c7c-ce34-428f-8563-35bce5201dc1",
                        label = "Minnesota"
                    )
                )
            ),
            Pair(
                "4915db69-55ca-4a00-b57e-893d7ea3e761",
                ResponseValue.ChoiceValue(
                    Choice(
                        id = "QQP6V2LnuOBK",
                        ref = "a66c1065-4e4f-46fc-8a26-794cc46a59f9",
                        label = "Adult, 18-64 years of age"
                    )
                )
            )
        )

        var position: Position
        try {
            position = form.firstPosition(skipWelcomeScreen = true, responses = responses)
        } catch (_ :Exception) {
            fail("Unexpected Exception")
        }

        when (position) {
            is Position.ScreenPosition -> {
                fail("Unexpected Position")
            }
            is Position.FieldPosition -> {
                assertEquals("0mMHJCj4JoPr", position.field.id)
            }
        }

        try {
            position = form.nextPosition(from = position, responses = responses)
        } catch (_ :Exception) {
            fail("Unexpected Exception")
        }

        when (position) {
            is Position.ScreenPosition -> {
                fail("Unexpected Position")
            }
            is Position.FieldPosition -> {
                assertEquals("0QSfTuGGV8W5", position.field.id)
            }
        }
    }
}