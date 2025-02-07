package com.typeform

import com.typeform.models.Position
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class ImageAttachmentTests : TypeformTestCase() {

    override val jsonResource: String
        get() = "ImageAttachment.json"

    @Test
    fun testDecode() {
        assertEquals("bjyxIhXZ", form.id)
    }

    @Test
    fun testFormStart() {
        val firstPosition = form.firstPosition(
            skipWelcomeScreen = false,
            responses = mutableMapOf(),
        )

        when (firstPosition) {
            is Position.FieldPosition -> {
                assertEquals("21xteNrP8VJs", firstPosition.field.id)
            }
            is Position.ScreenPosition -> {
                fail("Unexpected Position $firstPosition")
            }
        }
    }
}
