package com.typeform

import com.typeform.models.ResponseValue
import com.typeform.models.Responses
import com.typeform.models.invalidResponseValuesGiven
import com.typeform.models.validResponseValuesGiven
import com.typeform.schema.Choice
import java.util.Date
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ResponsesTests: TypeformTestCase() {

    private var responses: Responses = mutableMapOf()

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
        responses = mutableMapOf(
            Pair(visitReason, ResponseValue.ChoiceValue(visitReasonChoice)),
        )
        assertTrue(responses.validResponseValuesGiven(form.fields))
    }

    @Test
    fun testInvalidVisitReasonResponses() {
        responses = mutableMapOf(
            Pair(visitReason, ResponseValue.ChoicesValue(listOf(visitReasonChoice))),
        )
        assertFalse(responses.validResponseValuesGiven(form.fields))
    }

    @Test
    fun testValidResponseValueTypes() {
        responses = mutableMapOf(
            Pair(date, ResponseValue.DateValue(Date())),
            Pair(dropdown, ResponseValue.ChoiceValue(Choice())),
            Pair(longText, ResponseValue.StringValue("")),
            Pair(multipleChoice_Many, ResponseValue.ChoicesValue(emptyList())),
            Pair(multipleChoice_One, ResponseValue.ChoiceValue(Choice())),
            Pair(number, ResponseValue.IntValue(0)),
            Pair(rating, ResponseValue.IntValue(0)),
            Pair(shortText, ResponseValue.StringValue("")),
            Pair(yesNo, ResponseValue.BooleanValue(false)),
        )
        assertTrue(responses.validResponseValuesGiven(form.fields))
    }

    @Test
    fun testInvalidResponseValueTypes() {
        responses = mutableMapOf(
            Pair(date, ResponseValue.ChoiceValue(Choice())),
            Pair(dropdown, ResponseValue.DateValue(Date())),
            Pair(longText, ResponseValue.ChoiceValue(Choice())),
            Pair(multipleChoice_Many, ResponseValue.StringValue("")),
            Pair(multipleChoice_One, ResponseValue.ChoicesValue(emptyList())),
            Pair(number, ResponseValue.StringValue("")),
            Pair(rating, ResponseValue.BooleanValue(false)),
            Pair(shortText, ResponseValue.IntValue(0)),
            Pair(yesNo, ResponseValue.IntValue(0)),
        )
        val invalidKeys = responses.invalidResponseValuesGiven(form.fields)
        assertEquals(9, invalidKeys.count())
        assertEquals(
            listOf(
                date,
                dropdown,
                longText,
                multipleChoice_Many,
                multipleChoice_One,
                number,
                rating,
                shortText,
                yesNo,
            ),
            invalidKeys,
        )
    }
}