package com.typeform

import com.typeform.models.Position
import com.typeform.models.ResponseValue
import com.typeform.models.Responses
import com.typeform.schema.Choice
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class HealthHistoryFormTests : TypeformTestCase() {

    override val jsonResource: String
        get() = "HealthHistoryV3.json"

    @Test
    fun testDecode() {
        assertEquals("dmsX77W1", form.id)
    }

    @Test
    fun testMinorImmunization() {
        val responses: Responses = mutableMapOf(
            Pair("health-history-form-completed", ResponseValue.BooleanValue(false)),
            Pair("patient-age", ResponseValue.IntValue(17)),
            Pair(
                "sex-assigned-at-birth", ResponseValue.ChoiceValue(
                    Choice(id = "gYxjsy4loTDH", ref = "sex-assigned-at-birth-answer-male", label = "Male")
                )
            ),
            Pair("medications-usage", ResponseValue.BooleanValue(false)),
            Pair("medication-allergies", ResponseValue.BooleanValue(false)),
            Pair("surgeries", ResponseValue.BooleanValue(false)),
            Pair("family-medical-history", ResponseValue.StringValue("")),
        )

        val lastField = assertUnwrap(form.fieldWithRef("family-medical-history"))
        val position = Position.FieldPosition(lastField, null)
        when (val next = form.nextPosition(from = position, responses = responses)) {
            is Position.ScreenPosition -> {
                fail("Unexpected Position")
            }
            is Position.FieldPosition -> {
                assertEquals("minor-immunization-status", next.field.ref)
            }
        }
    }
}
