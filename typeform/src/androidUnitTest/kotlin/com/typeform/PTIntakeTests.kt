package com.typeform

import com.typeform.models.Position
import com.typeform.models.ResponseValue
import com.typeform.models.Responses
import com.typeform.schema.Choice
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class PTIntakeTests : TypeformTestCase() {

    override val jsonResource: String
        get() = "PTIntake42.json"

    @Test
    fun testLastFieldToThankYouScreen() {
        val responses: Responses = mutableMapOf(
            Pair("age-category", ResponseValue.IntValue(30)),
            Pair(
                "biological-sex",
                ResponseValue.ChoiceValue(
                    Choice(
                        id = "AlXZD8PosUqK",
                        label = "Male",
                        ref = "biological-sex-answer-male",
                    ),
                ),
            ),
            Pair("health-history-form-completed", ResponseValue.BooleanValue(true)),
            Pair("health-history-changed", ResponseValue.BooleanValue(false)),
            Pair(
                "reason-for-visit-physical-therapy-symptoms-location",
                ResponseValue.ChoicesValue(
                    listOf(
                        Choice(
                            id = "rTLcSvPGbEE6",
                            label = "Low back",
                            ref = "reason-for-visit-physical-therapy-symptoms-location-answer-low-back",
                        ),
                    ),
                ),
            ),
            Pair(
                "reason-for-visit-physical-therapy-symptoms",
                ResponseValue.ChoicesValue(
                    listOf(
                        Choice(
                            id = "lpOqwXIWFqVQ",
                            label = "Stiffness or aching",
                            ref = "reason-for-visit-physical-therapy-symptoms-answer-stiffness",
                        ),
                    ),
                ),
            ),
            Pair(
                "reason-for-visit-physical-therapy-symptoms-duration",
                ResponseValue.ChoiceValue(
                    Choice(
                        id = "w0WxgI0n6ias",
                        label = "Less than 2 weeks",
                        ref = "reason-for-visit-physical-therapy-symptoms-duration-answer-one-week",
                    ),
                ),
            ),
            Pair("49bbbbb5-b2db-4fe6-9257-2cea7a21159f", ResponseValue.IntValue(1)),
            Pair("8bad022d-fb6d-497c-a4c2-ade0ad99d696", ResponseValue.IntValue(1)),
            Pair("reason-for-visit-physical-therapy-symptoms-effect", ResponseValue.StringValue("pain")),
            Pair("reason-for-visit-physical-therapy-symptoms-effect-severity", ResponseValue.IntValue(1)),
            Pair(
                "87249736-4231-4da0-b626-50c48dc91c27",
                ResponseValue.ChoiceValue(
                    Choice(
                        id = "CcH9VF3OO56t",
                        label = "Nearly every day",
                        ref = "4bc77e92-c70c-442b-aa87-09aac3df4eea",
                    ),
                ),
            ),
            Pair(
                "c0dde0d4-0f31-45fd-ba13-d460ad13cb32",
                ResponseValue.ChoiceValue(
                    Choice(
                        id = "GQSWmGIq0f3k",
                        label = "Not at all",
                        ref = "a8ccca7e-37d2-409d-b52f-53f7e8aa8712",
                    ),
                ),
            ),
            Pair("ab1805b7-1cba-474c-aee3-1a2a17683b8c", ResponseValue.IntValue(1)),
            Pair("187a3247-605d-467d-9477-bc4e13c476b5", ResponseValue.IntValue(1)),
            Pair("ccc8ef20-2cbc-48e7-aaf5-512b5ba6cf88", ResponseValue.IntValue(1)),
            Pair("reason-for-visit-physical-therapy-detail", ResponseValue.StringValue("info")),
        )

        var position: Position
        try {
            position = form.firstPosition(skipWelcomeScreen = false, responses = responses)
        } catch (_: Exception) {
            fail("Unexpected Exception")
        }

        when (position) {
            is Position.FieldPosition -> {
                // first statement
                assertEquals("iJolnxu6Sftn", position.field.id)
            }
            else -> {
                fail("Unexpected Position")
            }
        }

        position = form.nextPosition(from = position, responses = responses)

        when (position) {
            is Position.FieldPosition -> {
                // first statement
                assertEquals("cLKRIr0wbDP9", position.field.id)
            }
            else -> {
                fail("Unexpected Position")
            }
        }

        try {
            position = form.nextPosition(position, responses)
        } catch (_: Exception) {
            fail("Unexpected Exception")
        }

        when (position) {
            is Position.FieldPosition -> {
                // reason-for-visit-physical-therapy
                assertEquals("XrGAAaW3t7BW", position.field.id)
            }
            else -> {
                fail("Unexpected Position")
            }
        }

        try {
            position = form.nextPosition(position, responses)
        } catch (_: Exception) {
            fail("Unexpected Exception")
        }

        when (position) {
            is Position.ScreenPosition -> {
                assertEquals("DefaultTyScreen", position.screen.id)
            }
            else -> {
                fail("Unexpected Position")
            }
        }
    }
}
