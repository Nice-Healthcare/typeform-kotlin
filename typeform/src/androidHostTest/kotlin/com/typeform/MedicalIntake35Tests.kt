package com.typeform

import com.typeform.models.Position
import com.typeform.models.ResponseValue
import com.typeform.models.Responses
import com.typeform.schema.structure.Choice
import kotlin.test.Test
import kotlin.test.assertEquals

class MedicalIntake35Tests : TypeformTestCase() {

    override val jsonResource: String
        get() = "MedicalIntake35.json"

    private var responses: Responses = mapOf()

    @Test
    fun testMultipleLogicPaths() {
        responses = mapOf(
            Pair(
                "appointment-state",
                ResponseValue.ChoiceValue(
                    Choice(
                        id = "0H6r4PQIWFI6",
                        ref = "aa028c7c-ce34-428f-8563-35bce5201dc1",
                        label = "Minnesota",
                    ),
                ),
            ),
            Pair("medicaid-enrolled", ResponseValue.BooleanValue(false)),
            Pair(
                "age-category",
                ResponseValue.ChoiceValue(
                    Choice(
                        id = "GGVFPlEB66Zp",
                        ref = "a66c1065-4e4f-46fc-8a26-794cc46a59f9",
                        label = "Adult, 18-64 years of age",
                    ),
                ),
            ),
            Pair(
                "biological-sex",
                ResponseValue.ChoiceValue(
                    Choice(
                        id = "AmCrMMmSX4z1",
                        ref = "a5e3cd94-58f6-4701-8632-dc3cc030dde5",
                        label = "Male",
                    ),
                ),
            ),
            Pair("health-history-form-completed", ResponseValue.BooleanValue(true)),
            Pair("health-history-changed", ResponseValue.BooleanValue(false)),
            Pair(
                "reason-for-visit-category",
                ResponseValue.ChoiceValue(
                    Choice(
                        id = "sv3WlByh2Hlw",
                        ref = "3faf5f88-5171-4933-af0f-210716bf1a60",
                        label = "Acute Care: Treat recent symptoms like a cough, sore throat, rash, etc.",
                    ),
                ),
            ),
            Pair(
                "reason-for-visit-acute",
                ResponseValue.ChoiceValue(
                    Choice(
                        id = "tmJ0fxcOga9e",
                        ref = "3c7619c0-14fe-4844-8910-d6e67999beb2",
                        label = "Allergy symptoms",
                    ),
                ),
            ),
            Pair("reason-for-visit-acute-follow-up", ResponseValue.BooleanValue(false)),
            Pair("reason-for-visit-acute-symptoms-other", ResponseValue.StringValue("Other symptoms")),
        )

        val currentGroupField = assertUnwrap(form.fieldWithRef("reason-for-visit-acute-intro"))
        val currentGroup = assertUnwrap(currentGroupField.properties.asGroup())
        val currentField = assertUnwrap(form.fieldWithRef("reason-for-visit-acute-symptoms-other"))
        val nextField = assertUnwrap(form.fieldWithRef("reason-for-visit-acute-symptoms-duration"))

        val currentPosition = Position.FieldPosition(
            field = currentField,
            group = currentGroup,
        )
        val next = form.nextPosition(from = currentPosition, responses = responses)
        val group = assertUnwrap(next.associatedGroup())
        val field = assertUnwrap(next.associatedField())
        assertEquals(group, currentGroup)
        assertEquals(field, nextField)
    }
}
