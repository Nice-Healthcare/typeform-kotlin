package com.typeform

import kotlin.test.Test
import kotlin.test.assertEquals

class PrimaryCareFormTests : TypeformTestCase() {

    override val jsonResource: String
        get() = "PrimaryCareV3.json"

    @Test
    fun testDecode() {
        assertEquals("hdgks8bb", form.id)
    }
}
