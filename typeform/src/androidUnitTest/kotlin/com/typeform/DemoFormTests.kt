package com.typeform

import com.typeform.models.Position
import com.typeform.schema.FieldType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.fail

class DemoFormTests : TypeformTestCase() {

    override val jsonResource: String
        get() = "DemoForm.json"

    @Test
    fun testDecode() {
        assertEquals("hu2FodCY", form.id)
    }

    @Test
    fun testFormStart() {
        var position = form.firstPosition(
            skipWelcomeScreen = false,
            responses = mutableMapOf(),
        )

        when (position) {
            is Position.FieldPosition -> {
                fail("Unexpected Position $position")
            }
            is Position.ScreenPosition -> {
                assertEquals("iVVVXbYhFAqt", position.screen.id)
            }
        }

        position = form.nextPosition(position, mutableMapOf())

        when (position) {
            is Position.FieldPosition -> {
                assertEquals("UKrtkRy8EAY2", position.field.id)
                assertEquals(FieldType.GROUP, position.field.type)
            }
            is Position.ScreenPosition -> {
                fail("Unexpected Position $position")
            }
        }

        position = form.nextPosition(position, mutableMapOf())

        when (position) {
            is Position.FieldPosition -> {
                assertEquals("M6IDI3o5xRXu", position.field.id)
                assertNotNull(position.group)
            }
            is Position.ScreenPosition -> {
                fail("Unexpected Position $position")
            }
        }
    }
}
