package com.typeform

import kotlin.test.Test
import kotlin.test.assertEquals

class PhysicalTherapyFormTests : TypeformTestCase() {

    override val jsonResource: String
        get() = "PhysicalTherapyV3.json"

    @Test
    fun testDecode() {
        assertEquals("rGeAA7V5", form.id)
    }
}
