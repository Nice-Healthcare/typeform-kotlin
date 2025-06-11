package com.typeform

import com.typeform.Typeform.Companion.json
import com.typeform.models.ResponseValue
import com.typeform.models.Responses
import com.typeform.models.invalidResponseValuesGiven
import com.typeform.models.validResponseValuesGiven
import com.typeform.schema.Choice
import java.util.Calendar
import java.util.Date
import java.util.TimeZone
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlinx.serialization.encodeToString

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
            Pair(DATE, ResponseValue.DateValue(Date())),
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
            Pair(DROPDOWN, ResponseValue.DateValue(Date())),
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

    @Test
    fun testDefaultResponseValueEncoding() {
        responses = mapOf(
            "one" to ResponseValue.IntValue(6),
            "two" to ResponseValue.ChoiceValue(
                Choice(
                    id = "s83hchg83",
                    ref = "sex-at-birth",
                    label = "Female",
                ),
            ),
            "three" to ResponseValue.StringValue("Hello"),
        )
        val json = Typeform.json.encodeToString(responses)
        assertEquals(
            """{"one":{"int":6},"two":{"choice":{"id":"s83hchg83","ref":"sex-at-birth","label":"Female"}},"three":{"string":"Hello"}}""",
            json,
        )
    }

    @Test
    fun testDefaultResponseValueDecoding() {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Chicago"))
        calendar.set(2025, 5, 10, 13, 27, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val date = calendar.time

        val json = """
        {
          "553D6E8C-5562-4A7B-89AF-9C8428428197": {
            "bool" : false
          },
          "alpha" : {
            "choice" : {
              "id" : "something",
              "label" : "Battlestar",
              "ref" : "70675439-3BA3-44F9-8807-949A5375ACEC"
            }
          },
          "beta" : {
            "date" : "2025-06-10T13:27:00-05:00"
          }
        }
        """

        responses = Typeform.json.decodeFromString(json)
        assertEquals(
            mapOf(
                "553D6E8C-5562-4A7B-89AF-9C8428428197" to ResponseValue.BooleanValue(false),
                "alpha" to ResponseValue.ChoiceValue(
                    Choice(
                        id = "something",
                        ref = "70675439-3BA3-44F9-8807-949A5375ACEC",
                        label = "Battlestar",
                    ),
                ),
                "beta" to ResponseValue.DateValue(date),
            ),
            responses,
        )
    }
}
