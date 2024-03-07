package com.typeform

import kotlin.test.Test
import kotlin.test.assertEquals

class HealthHistoryFormTests : TypeformTestCase() {

    override val jsonResource: String
        get() = "HealthHistoryV3.json"

    @Test
    fun testDecode() {
        assertEquals("dmsX77W1", form.id)
    }
}
