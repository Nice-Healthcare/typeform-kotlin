package com.typeform

import com.typeform.models.ResponseValue
import com.typeform.models.Responses
import com.typeform.models.invalidResponseValuesGiven
import com.typeform.models.validResponseValuesGiven
import com.typeform.schema.Choice
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlinx.datetime.Clock

class ResponsesTests : TypeformTestCase() {

    private var responses: Responses = mapOf()

    private val visitReasonChoice: Choice
        get() {
            return Choice(
                id = "Sx6ZqqmkAHOI",
                ref = "4f6732f3-d3b6-4c83-9c3e-a4945a004507",
                label = "An update or follow-up on how you are feeling regarding an illness or injury that was discussed with a Specialty medical provider",
            )
        }

    @Test
    fun testValidVisitReasonResponses() {
        responses = mapOf(
            Pair(VISIT_REASON, ResponseValue.ChoiceValue(visitReasonChoice)),
        )
        assertTrue(responses.validResponseValuesGiven(form.fields))
    }

    @Test
    fun testInvalidVisitReasonResponses() {
        responses = mapOf(
            Pair(VISIT_REASON, ResponseValue.ChoicesValue(listOf(visitReasonChoice))),
        )
        assertFalse(responses.validResponseValuesGiven(form.fields))
    }

    @Test
    fun testValidResponseValueTypes() {
        responses = mapOf(
            Pair(DATE, ResponseValue.InstantValue(Clock.System.now())),
            Pair(DROPDOWN, ResponseValue.ChoiceValue(Choice())),
            Pair(LONG_TEXT, ResponseValue.StringValue("")),
            Pair(MULTIPLE_CHOICE_MANY, ResponseValue.ChoicesValue(emptyList())),
            Pair(MULTIPLE_CHOICE_ONE, ResponseValue.ChoiceValue(Choice())),
            Pair(NUMBER, ResponseValue.IntValue(0)),
            Pair(RATING, ResponseValue.IntValue(0)),
            Pair(SHORT_TEXT, ResponseValue.StringValue("")),
            Pair(YES_NO, ResponseValue.BooleanValue(false)),
        )
        assertTrue(responses.validResponseValuesGiven(form.fields))
    }

    @Test
    fun testInvalidResponseValueTypes() {
        responses = mapOf(
            Pair(DATE, ResponseValue.ChoiceValue(Choice())),
            Pair(DROPDOWN, ResponseValue.InstantValue(Clock.System.now())),
            Pair(LONG_TEXT, ResponseValue.ChoiceValue(Choice())),
            Pair(MULTIPLE_CHOICE_MANY, ResponseValue.StringValue("")),
            Pair(MULTIPLE_CHOICE_ONE, ResponseValue.ChoicesValue(emptyList())),
            Pair(NUMBER, ResponseValue.StringValue("")),
            Pair(RATING, ResponseValue.BooleanValue(false)),
            Pair(SHORT_TEXT, ResponseValue.IntValue(0)),
            Pair(YES_NO, ResponseValue.IntValue(0)),
        )
        val invalidKeys = responses.invalidResponseValuesGiven(form.fields)
        assertEquals(9, invalidKeys.count())
        assertEquals(
            listOf(
                DATE,
                DROPDOWN,
                LONG_TEXT,
                MULTIPLE_CHOICE_MANY,
                MULTIPLE_CHOICE_ONE,
                NUMBER,
                RATING,
                SHORT_TEXT,
                YES_NO,
            ),
            invalidKeys,
        )
    }
}
