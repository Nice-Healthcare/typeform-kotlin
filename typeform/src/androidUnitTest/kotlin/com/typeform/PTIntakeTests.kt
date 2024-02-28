package com.typeform

import kotlin.test.Test
import kotlin.test.assertEquals

class PTIntakeTests : TypeformTestCase() {

    override val jsonResource: String
        get() = "PTIntake42.json"

    @Test
    fun testDecoding() {
        assertEquals(30, form.fields.count())
        assertEquals(19, form.logic.count())
    }
}